package it.itvcoretechtest.service

import cats.effect.IO
import it.itvcoretechtest.{ThumbnailAppError, VideoAssetId}

sealed trait ThumbnailGenerationError extends ThumbnailAppError
case object TimestampOutOfBounds extends ThumbnailGenerationError

trait ThumbnailGenerator {
  def generateThumbnail(assetId: VideoAssetId): IO[Either[ThumbnailGenerationError, Unit]]
}

class ThumbnailGeneratorService extends ThumbnailGenerator {
  override def generateThumbnail(assetId: VideoAssetId): IO[Either[ThumbnailGenerationError, Unit]] = ???
}
