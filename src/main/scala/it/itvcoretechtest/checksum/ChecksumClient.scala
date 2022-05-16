package it.itvcoretechtest.checksum

import cats.effect.IO
import fs2.io.file.{Files, Path}
import fs2.{hash, text}
import it.itvcoretechtest._
import org.http4s.Status.NotFound
import org.http4s.client.Client
import org.http4s.{Request, Uri}
import org.http4s.circe.CirceEntityCodec._

import scala.annotation.unused


trait ChecksumClient {
  def getChecksum(assetId: VideoAssetId): IO[Either[MetadataResponseError, MetadataResponse]]

  def calculateAssetChecksum(fiilePath: Filepath): IO[Either[ChecksumCalculationError, CalculatedChecksum]]
}

class ChecksumClientImpl(@unused client: Client[IO], @unused baseUri: Uri) extends ChecksumClient {
  override def getChecksum(assetId: VideoAssetId): IO[Either[MetadataResponseError, MetadataResponse]] = {
    client.run(Request[IO](uri = baseUri / assetId.value / "metadata")).use(r => r.status match {
      case s if s.isSuccess => r.attemptAs[MetadataResponse].leftMap(_ => DecodeFailure).value
      case NotFound => IO.pure(Left(MetadataNotFound))
      case e@_ => IO.pure(Left(OtherError(e, e.reason)))
    })


  }




  override def calculateAssetChecksum(filepath: Filepath): IO[Either[ChecksumCalculationError, CalculatedChecksum]] = for {
    md5 <- Files[IO]
      .readAll(Path(filepath.value))
      .through(hash.md5)
      .through(text.hex.encode)
      .map(Md5(_))
      .compile
      .last

    sha1 <- Files[IO]
      .readAll(Path(filepath.value))
      .through(hash.sha1)
      .through(text.hex.encode)
      .map(Sha1(_))
      .compile
      .last


    sha256 <- Files[IO]
      .readAll(Path(filepath.value))
      .through(hash.sha256)
      .through(text.hex.encode)
      .map(Sha256(_))
      .compile
      .last


  } yield (md5, sha1, sha256) match {
    case (Some(m), Some(s1), Some(s256)) => Right(CalculatedChecksum(m, s1, s256))
    case (None, _, _) => Left(Md5CalculationFailure)
    case (_, None, _) => Left(Sha1CalculationFailure)
    case (_, _, None) => Left(Sha256CalculationFailure)
  }

}



