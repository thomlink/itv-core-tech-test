package it.itvcoretechtest.feature.test

import cats.effect.IO
import it.itvcoretechtest
import it.itvcoretechtest.feature.AppState
import it.itvcoretechtest.service.{ThumbnailGenerationError, ThumbnailGenerator}

class StubThumbnailGenerator(appState: AppState) extends ThumbnailGenerator{
  override def generateThumbnail(assetId: itvcoretechtest.VideoAssetId): IO[Either[ThumbnailGenerationError, Unit]] = ???
}
