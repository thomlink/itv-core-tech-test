package it.itvcoretechtest.feature.test

import cats.data.EitherT
import cats.effect.IO
import it.itvcoretechtest
import it.itvcoretechtest.checksum
import it.itvcoretechtest.checksum.ChecksumClient
import it.itvcoretechtest.feature.AppState

class StubChecksumClient(appState: AppState) extends ChecksumClient {
  override def getChecksum(assetId: itvcoretechtest.VideoAssetId): IO[Either[checksum.MetadataResponseError, checksum.MetadataResponse]] = ???

  override def calculateAssetChecksum(assetId: itvcoretechtest.VideoAssetId): IO[Either[checksum.ChecksumCalculationError, checksum.CalculatedChecksum]] = ???
}
