package it.itvcoretechtest

import cats.effect.{ExitCode, IO, IOApp}
import it.itvcoretechtest.config.Config.loadConfig
import it.itvcoretechtest.http.{HttpMetadataClient, MetadataClient}
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
        thumbnailService = new ThumbnailApp(metadataClient)

        _ <- thumbnailService.run(videoAssetId, timestamp, filepath)

      } yield ExitCode.Success
    }
  }
}
