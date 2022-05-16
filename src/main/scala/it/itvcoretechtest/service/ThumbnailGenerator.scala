package it.itvcoretechtest.service

import cats.effect.IO
import it.itvcoretechtest.{ThumbnailAppError, ThumbnailTimestamp, VideoAssetId}

sealed trait ThumbnailGenerationError extends ThumbnailAppError
case object TimestampOutOfBounds extends ThumbnailGenerationError

trait ThumbnailGenerator {
  def generateThumbnail(assetId: VideoAssetId, timestamp: ThumbnailTimestamp): IO[Either[ThumbnailGenerationError, Unit]]
}

class ThumbnailGeneratorService extends ThumbnailGenerator {
  override def generateThumbnail(assetId: VideoAssetId, timestamp: ThumbnailTimestamp): IO[Either[ThumbnailGenerationError, Unit]] = ???
}
