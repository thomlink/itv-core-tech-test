package it.itvcoretechtest

import cats.effect.{ExitCode, IO, IOApp}
import it.itvcoretechtest.checksum.ChecksumClientImpl
import it.itvcoretechtest.config.Config.loadConfig
import it.itvcoretechtest.service.{ChecksumVerifierService, ThumbnailGeneratorService}
import org.http4s.blaze.client.BlazeClientBuilder

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeClientBuilder[IO].resource.use { client =>
      for {

        (filepath, timestamp, videoAssetId) <- args match {
          case h :: t :: a :: Nil => t.toIntOption match {
            case Some(value) => IO.pure((Filepath(h), ThumbnailTimestamp(value), VideoAssetId(a)))
            case None => IO.raiseError(new IllegalArgumentException("""Timestamp needs to be an Int""""))
          }
          case _ => IO.raiseError(new IllegalArgumentException("""Usage: sbt run "<filepath> <timestamp> <assetId>""""))
        }

        config <- loadConfig

        checksumCalculator = new ChecksumClientImpl(client, config.itvBaseUri)
        checksumService = new ChecksumVerifierService(checksumCalculator)
        thumbnailGenerator = new ThumbnailGeneratorService

        thumbnailApp = new ThumbnailApp(checksumService, thumbnailGenerator)

        r <- thumbnailApp.generateThumbnail(videoAssetId, timestamp, filepath).value

      } yield r match {
        case Left(e) => {
          println(e)
          ExitCode.Error
        }
        case Right(_) => ExitCode.Success
      }
    }
  }
}
