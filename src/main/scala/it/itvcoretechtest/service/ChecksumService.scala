package it.itvcoretechtest.service

import cats.effect.IO
import it.itvcoretechtest.{Filepath, VideoAssetId}
import it.itvcoretechtest.checksum.{CalculatedChecksum, ChecksumClient, MetadataResponse}

trait ChecksumService {
  def verifyChecksum(assetId: VideoAssetId, filePath: Filepath): IO[Either[ChecksumValidationFailure, Unit]]
}

class ChecksumVerifierService(checksumClient: ChecksumClient) extends ChecksumService {
  override def verifyChecksum(assetId: VideoAssetId, filePath: Filepath): IO[Either[ChecksumValidationFailure, Unit]] = for {
    fetchedChecksumResult <- checksumClient.getChecksum(assetId)
    calculatedChecksumResult <- checksumClient.calculateAssetChecksum(filePath)
  } yield (fetchedChecksumResult, calculatedChecksumResult) match {
    case (Left(e), _) => Left(e)
    case (_, Left(e)) => Left(e)
    case (Right(m), Right(c)) => if (doChecksumsMatch(m, c)) Right(()) else Left(InvalidChecksum)
  }

  private def doChecksumsMatch(metadata: MetadataResponse, checksum: CalculatedChecksum): Boolean =
    metadata.md5 == checksum.md5 &&
    metadata.sha1 == checksum.sha1 &&
    metadata.sha256 == checksum.sha256
}
