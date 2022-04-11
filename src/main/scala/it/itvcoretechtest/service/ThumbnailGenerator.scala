package it.itvcoretechtest.service

import cats.data.EitherT
import cats.effect.IO
import it.itvcoretechtest.{ThumbnailAppError, VideoAssetId}

sealed trait ThumbnailGenerationError extends ThumbnailAppError
case class SomeError(desc: String) extends ThumbnailGenerationError

trait ThumbnailGenerator {
  def generateThumbnail(assetId: VideoAssetId): IO[Either[ThumbnailGenerationError, Unit]]
}

class ThumbnailGeneratorService extends ThumbnailGenerator {
  override def generateThumbnail(assetId: VideoAssetId): IO[Either[ThumbnailGenerationError, Unit]] = ???
}
