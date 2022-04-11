package it.itvcoretechtest

import cats.effect.{ExitCode, IO, IOApp}
import it.itvcoretechtest.checksum.ChecksumCalculatorImpl
import it.itvcoretechtest.config.Config.loadConfig
import it.itvcoretechtest.http.{HttpMetadataClient, MetadataClient}
import it.itvcoretechtest.service.{ChecksumService, ChecksumVerifierService, ThumbnailGeneratorService}
import org.http4s.blaze.client.BlazeClientBuilder

object Main extends IOApp {



  override def run(args: List[String]): IO[ExitCode] = {
    BlazeClientBuilder[IO].resource.use { client =>
      for {

        (filepath, timestamp, videoAssetId) <- args match {
          case h :: t :: a :: Nil => t.toIntOption match {
            case Some(value) => IO.pure(Filepath(h), Timestamp(value), VideoAssetId(a))
            case None => IO.raiseError(new IllegalArgumentException("""Timestamp needs to be an Int""""))
          }
          case _ => IO.raiseError(new IllegalArgumentException("""Usage: sbt run "<filepath> <timestamp> <assetId>""""))
        }

        config <- loadConfig

        metadataClient = new HttpMetadataClient(config.itvBaseUri, client)
        checksumCalculator = new ChecksumCalculatorImpl(metadataClient)
        checksumService = new ChecksumVerifierService(checksumCalculator)
        thumbnailGenerator = new ThumbnailGeneratorService

        thumbnailApp = new ThumbnailApp(checksumService, thumbnailGenerator)

        result <- thumbnailApp.run(videoAssetId, timestamp, filepath)

      } yield result match {
        case Left(error) => {
          println(error.toString)
          ExitCode.Error
        }
        case Right(_) => ExitCode.Success
      }
    }
  }
}
