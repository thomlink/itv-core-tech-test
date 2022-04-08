package it.itvcoretechtest

import cats.data.EitherT
import cats.effect.IO
import it.itvcoretechtest.http.MetadataClient
import it.itvcoretechtest.service.{ChecksumService, ThumbnailGenerator}


trait ThumbnailAppError
case object ChecksumFailure extends ThumbnailAppError
case object ThumbnailGenerationFailure extends ThumbnailAppError
case class OtherError(desc: String) extends ThumbnailAppError


case class TimestampImage()

trait App {
  def run(assetId: VideoAssetId, timestamp: Timestamp, filepath: Filepath): EitherT[IO, ThumbnailAppError, Unit]
}

class ThumbnailApp(checksumService: ChecksumService, thumbnailGenerator: ThumbnailGenerator) extends App {


  override def run(assetId: VideoAssetId, timestamp: Timestamp, filepath: Filepath): EitherT[IO, ThumbnailAppError, Unit] = {
    for {
      checksumResult <- checksumService.verifyChecksum(assetId)
      thumbnailGenerationResult <- thumbnailGenerator.generateThumbnail(assetId).map(_ => Unit).leftMap(_ => ThumbnailGenerationFailure)
    }  yield ()

  }
}
