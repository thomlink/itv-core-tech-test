package it.itvcoretechtest.http

import cats.data.EitherT
import cats.effect.IO
import cats.implicits.{catsSyntaxApplicativeId, toBifunctorOps}
import io.circe.syntax.EncoderOps
import it.itvcoretechtest.{AssetId, AssetNotFound, CustomErrorType}
import org.http4s.{Request, Status}
import org.http4s.Status.NotFound
import org.http4s.circe.CirceEntityCodec._
import org.http4s.client.Client
import org.http4s.implicits.http4sLiteralsSyntax


sealed trait MetadataResponseError
case object MetadataNotFound extends MetadataResponseError
case object DecodeFailure extends MetadataResponseError
case class OtherError(status: Status, description: String) extends MetadataResponseError

trait MetadataClient {
  def getMeta(assetId: AssetId): EitherT[IO, CustomErrorType, MetaResponse]
  def getAsset(assetId: AssetId): EitherT[IO, CustomErrorType, AssetResponse]
}

class HttpMetadataClient(client: Client[IO]) extends MetadataClient {
  val baseUri = uri"https://ct-tech-test.play.cloud.itv.com/playground"

  override def getMeta(assetId: AssetId): EitherT[IO, MetadataResponseError, MetaResponse] = {
    val x: EitherT[IO, MetadataResponseError, MetaResponse] = client.run(Request[IO](uri = baseUri / assetId.value / "metadata")).use(r => r.status match {
      case s if s.isSuccess => r.attemptAs[MetaResponse].leftMap(_ => DecodeFailure).leftWiden[MetadataResponseError]
      case NotFound         => EitherT.leftT[IO, MetaResponse](MetadataNotFound).leftWiden[MetadataResponseError]
      case e @ _            => EitherT.leftT[IO, MetaResponse](OtherError(e, e.reason)).leftWiden[MetadataResponseError]
    })
    x
  }


}
