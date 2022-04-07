package it.itvcoretechtest

import io.circe.generic.JsonCodec

package object http {
  @JsonCodec(encodeOnly = true)
  case class ThumbnailResponse()

  @JsonCodec(decodeOnly = true)
  case class MetaResponse(hash: String)

  @JsonCodec(decodeOnly = true)
  case class AssetResponse()
}
