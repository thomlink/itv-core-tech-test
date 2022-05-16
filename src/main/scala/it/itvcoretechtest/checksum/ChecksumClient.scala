package it.itvcoretechtest.checksum

import cats.effect.IO
import cats.implicits._
import fs2.io.file.{Files, Path}
import fs2.{hash, text}
import it.itvcoretechtest._
import org.http4s.Status.NotFound
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.client.Client
import org.http4s.{Request, Uri}

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
      .compile
      .last


    a = Either.fromOption(md5, Md5CalculationFailure).map(Md5(_))


    sha1 <- Files[IO]
      .readAll(Path(filepath.value))
      .through(hash.sha1)
      .through(text.hex.encode)
      .compile
      .last



    b = Either.fromOption(sha1, Sha1CalculationFailure).map(Sha1(_))


    sha256 <- Files[IO]
      .readAll(Path(filepath.value))
      .through(hash.sha256)
      .through(text.hex.encode)
      .compile
      .last

    c = Either.fromOption(sha256, Sha256CalculationFailure).map(Sha256(_))



  } yield (a, b, c) match {
    case (Right(m), Right(s1), Right(s256)) => Right(CalculatedChecksum(m, s1, s256))
    case e @ _ => Left(OtherError)
  }

}



