package it.itvcoretechtest

import cats.effect.IO
import it.itvcoretechtest.service.{ChecksumService, ThumbnailGenerator}


trait ThumbnailAppError

case class TimestampImage()

trait App {
  def run(assetId: VideoAssetId, timestamp: Timestamp, filepath: Filepath): IO[Either[ThumbnailAppError, Unit]]
}

class ThumbnailApp(checksumService: ChecksumService, thumbnailGenerator: ThumbnailGenerator) extends App {

  override def run(assetId: VideoAssetId, timestamp: Timestamp, filepath: Filepath): IO[Either[ThumbnailAppError, Unit]] = {
    for {
      checksumResult <- checksumService.verifyChecksum(assetId)
      result <- checksumResult match {
        case e @ Left(_) => IO.pure(e)
        case _ => thumbnailGenerator.generateThumbnail(assetId)
      }
    }  yield result

  }
}
