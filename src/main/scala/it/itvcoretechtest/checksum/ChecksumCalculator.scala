package it.itvcoretechtest.checksum

import cats.data.EitherT
import cats.effect.IO
import it.itvcoretechtest.VideoAssetId
import it.itvcoretechtest.http.{AssetId, MetadataClient, MetadataResponse, MetadataResponseError}



trait ChecksumCalculator {
  def getChecksum(assetId: VideoAssetId): EitherT[IO, MetadataResponseError, MetadataResponse]
  def calculateAssetChecksum(assetId: VideoAssetId): EitherT[IO, MetadataResponseError, CalculatedChecksum]
}

class ChecksumCalculatorImpl(client: MetadataClient) extends ChecksumCalculator{
  override def getChecksum(assetId: VideoAssetId): EitherT[IO, MetadataResponseError, MetadataResponse] = client.getMetadata(AssetId(assetId.value))

  override def calculateAssetChecksum(assetId: VideoAssetId): EitherT[IO, MetadataResponseError, CalculatedChecksum] = ???
}


