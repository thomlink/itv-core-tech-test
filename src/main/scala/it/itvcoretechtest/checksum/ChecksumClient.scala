package it.itvcoretechtest.checksum

import cats.effect.IO
import it.itvcoretechtest.{Filepath, VideoAssetId}
import org.http4s.Uri
import org.http4s.client.Client

import scala.annotation.unused



trait ChecksumClient {
  def getChecksum(assetId: VideoAssetId): IO[Either[MetadataResponseError, MetadataResponse]]
  def calculateAssetChecksum(fiilePath: Filepath): IO[Either[ChecksumCalculationError, CalculatedChecksum]]
}

class ChecksumClientImpl(@unused client: Client[IO], @unused baseUri: Uri) extends ChecksumClient{
  override def getChecksum(assetId: VideoAssetId): IO[Either[MetadataResponseError, MetadataResponse]] = {
      //EitherT(
      //      client.run(Request[IO](uri = baseUri / assetId.value / "metadata")).use(r => r.status match {
      //        case s if s.isSuccess => r.attemptAs[MetaResponse].leftMap(_ => DecodeFailure).value
      //        case NotFound         => IO.pure(Left(MetadataNotFound))
      //        case e @ _            => IO.pure(Left(OtherError(e, e.reason)))
      //      })
      //    )
      ???

  }

  override def calculateAssetChecksum(filepath: Filepath): IO[Either[ChecksumCalculationError, CalculatedChecksum]] = ???
}


