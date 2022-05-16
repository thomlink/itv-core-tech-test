package it.itvcoretechtest.feature.test

import cats.data.EitherT
import cats.effect.IO
import it.itvcoretechtest
import it.itvcoretechtest.{Filepath, checksum}
import it.itvcoretechtest.checksum.{CalculatedChecksum, ChecksumCalculationError, ChecksumClient}
import it.itvcoretechtest.feature.AppState

class StubChecksumClient(appState: AppState) extends ChecksumClient {
  override def getChecksum(assetId: itvcoretechtest.VideoAssetId): IO[Either[checksum.MetadataResponseError, checksum.MetadataResponse]] = ???

  override def calculateAssetChecksum(fiilePath: Filepath): IO[Either[ChecksumCalculationError, CalculatedChecksum]] = ???
}
