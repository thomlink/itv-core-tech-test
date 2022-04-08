package it.itvcoretechtest.service

import cats.data.EitherT
import cats.effect.IO
import it.itvcoretechtest.VideoAssetId

sealed trait ThumbnailGenerationError
case class SomeError(desc: String) extends ThumbnailGenerationError

trait ThumbnailGenerator {
  def generateThumbnail(assetId: VideoAssetId): EitherT[IO, ThumbnailGenerationError, Unit]
}

class ThumbnailGeneratorService extends ThumbnailGenerator {
  override def generateThumbnail(assetId: VideoAssetId): EitherT[IO, ThumbnailGenerationError, Unit] = ???
}
