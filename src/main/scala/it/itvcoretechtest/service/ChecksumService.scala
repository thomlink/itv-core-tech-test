package it.itvcoretechtest.service

import cats.effect.IO
import it.itvcoretechtest.checksum.{CalculatedChecksum, ChecksumCalculator}
import it.itvcoretechtest.http.{AssetId, MetadataClient, MetadataResponse}
import it.itvcoretechtest.{Filepath, VideoAssetId}

import scala.annotation.unused

trait ChecksumService {
  def verifyChecksum(assetId: VideoAssetId): IO[ChecksumValidationResult]
}

class ChecksumVerifierService(calculator: ChecksumCalculator, metadataClient: MetadataClient) extends ChecksumService {
  override def verifyChecksum(assetId: VideoAssetId): IO[ChecksumValidationResult] = for {
    fetchedChecksum <- metadataClient.getMetadata(AssetId(assetId.value)).value
    calculatedChecksum <- calculator.calculateAssetChecksum(assetId).value
  } yield {
    fetchedChecksum match {
      case Left(value) => Failure("")
      case Right(metadata) => calculatedChecksum match {
        case Left(value) => Failure("")
        case Right(checksum) => if (doChecksumsMatch(metadata, checksum))
          Success
        else
          Failure("")
      }
    }
  }

  def doChecksumsMatch(@unused metadata: MetadataResponse, @unused checksum: CalculatedChecksum): Boolean = ???
}
