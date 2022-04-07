package it

import io.circe.generic.JsonCodec

package object itvcoretechtest {
  @JsonCodec(encodeOnly = true)
  case class AssetId(value: String)
  case class ThumbnailTimestamp(seconds: Int)
}
