package it.itvcoretechtest.service

import cats.effect.IO
import it.itvcoretechtest.AssetId

trait VideoVerifier {
  def getChecksum(assetId: AssetId): IO
  def verifyChecksum
}

class VideoVerifierImpl extends VideoVerifier {

}
