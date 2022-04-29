package it.itvcoretechtest

import io.circe.Decoder
import io.circe.generic.JsonCodec
import io.circe.generic.extras.semiauto.deriveUnwrappedDecoder
import it.itvcoretechtest.service.ChecksumValidationFailure
import org.http4s.Status

package checksum {
  case class CalculatedChecksum()

  sealed trait ChecksumCalculationError extends ChecksumValidationFailure
  case object ChecksumCalculationFailure extends ChecksumCalculationError


    @JsonCodec(encodeOnly = true)
    case class AssetId(value: String)

    case class Sha1(value: String)
    object Sha1 {
      implicit val d: Decoder[Sha1] = deriveUnwrappedDecoder
    }

    case class Sha256(value: String)
    object Sha256 {
      implicit val d: Decoder[Sha256] = deriveUnwrappedDecoder
    }

    case class Md5(value: String)
    object Md5 {
      implicit val d: Decoder[Md5] = deriveUnwrappedDecoder
    }

    case class Crc32(value: String)
    object Crc32 {
      implicit val d: Decoder[Crc32] = deriveUnwrappedDecoder
    }

    case class FrameRate(value: String)
    object FrameRate {
      implicit val d: Decoder[FrameRate] = deriveUnwrappedDecoder
    }

    case class Resolution(value: String)
    object Resolution {
      implicit val d: Decoder[Resolution] = deriveUnwrappedDecoder
    }

    case class DynamicRange(value: String)
    object DynamicRange {
      implicit val d: Decoder[DynamicRange] = deriveUnwrappedDecoder
    }

    case class ProductionId(value: String)
    object ProductionId {
      implicit val d: Decoder[ProductionId] = deriveUnwrappedDecoder
    }

    case class Title(value: String)
    object Title {
      implicit val d: Decoder[Title] = deriveUnwrappedDecoder
    }

    case class SeriesNumber(value: Int)
    object SeriesNumber {
      implicit val d: Decoder[SeriesNumber] = deriveUnwrappedDecoder
    }

    @JsonCodec(decodeOnly = true)
    case class EpisodeNumber(value: Int)
    object EpisodeNumber {
      implicit val d: Decoder[EpisodeNumber] = deriveUnwrappedDecoder
    }

    case class Duration(value: String)
    object Duration {
      implicit val d: Decoder[Duration] = deriveUnwrappedDecoder
    }

    @JsonCodec(decodeOnly = true)
    case class VideoQuality(
                             frameRate: FrameRate,
                             resolution: Resolution,
                             dynamicRange: DynamicRange
                           )

    @JsonCodec(decodeOnly = true)
    case class Identifiers(
                            productionId: ProductionId,
                            title: Title,
                            seriesNumber: Option[SeriesNumber],
                            episodeNumber: Option[EpisodeNumber],
                            duration: Duration
                          )


    @JsonCodec(decodeOnly = true)
    case class MetadataResponse(
                                 sha1: Sha1,
                                 sha256: Sha256,
                                 md5: Md5,
                                 crc32: Crc32,
                                 videoQuality: VideoQuality,
                                 identifiers: Identifiers
                               )


    sealed trait MetadataResponseError extends ChecksumValidationFailure
    case object MetadataNotFound extends MetadataResponseError
    case object DecodeFailure extends MetadataResponseError
    case class OtherError(status: Status, description: String) extends MetadataResponseError




}
