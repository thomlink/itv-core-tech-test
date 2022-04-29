package it.itvcoretechtest

import cats.data.EitherT
import cats.effect.IO
import it.itvcoretechtest.service.{ChecksumService, ThumbnailGenerator}


trait ThumbnailAppError

case class TimestampImage()

trait App {
  def generateThumbnail(assetId: VideoAssetId, timestamp: ThumbnailTimestamp, filepath: Filepath): EitherT[IO, ThumbnailAppError, Unit]
}

class ThumbnailApp(checksumService: ChecksumService, thumbnailGenerator: ThumbnailGenerator) extends App {

  override def generateThumbnail(assetId: VideoAssetId, timestamp: ThumbnailTimestamp, filepath: Filepath): EitherT[IO, ThumbnailAppError, Unit] =
    EitherT(checksumService.verifyChecksum(assetId, filepath).flatMap(_ => thumbnailGenerator.generateThumbnail(assetId)))

}
