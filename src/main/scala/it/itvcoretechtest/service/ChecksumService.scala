package it.itvcoretechtest.service

import cats.effect.IO
import it.itvcoretechtest.checksum.{CalculatedChecksum, ChecksumCalculator}
import it.itvcoretechtest.http.{AssetId, MetadataClient, MetadataResponse}
import it.itvcoretechtest.{Filepath, VideoAssetId}

import scala.annotation.unused

trait ChecksumService {
  def verifyChecksum(assetId: VideoAssetId): IO[Either[ChecksumValidationFailure, Unit]]
}

class ChecksumVerifierService(calculator: ChecksumCalculator) extends ChecksumService {
  override def verifyChecksum(assetId: VideoAssetId): IO[Either[ChecksumValidationFailure, Unit]] = for {
    fetchedChecksum <- calculator.getChecksum(assetId).value
    calculatedChecksum <- calculator.calculateAssetChecksum(assetId).value
  } yield {
    fetchedChecksum match {
      case Right(metadata) => calculatedChecksum match {
        case e2 @ Left(_) => e2
        case Right(checksum) if doChecksumsMatch(metadata, checksum) => Right(())
        case _ => Left(InvalidChecksum)
      }
      case e @ _ => e
    }
  }

  private def doChecksumsMatch(@unused metadata: MetadataResponse, @unused checksum: CalculatedChecksum): Boolean = ???
}
