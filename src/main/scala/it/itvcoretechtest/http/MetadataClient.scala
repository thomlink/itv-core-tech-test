package it.itvcoretechtest.http

import cats.data.EitherT
import cats.effect.IO
import it.itvcoretechtest.AssetId
import org.http4s.Status.NotFound
import org.http4s.circe.CirceEntityCodec._
import org.http4s.client.Client
import org.http4s.{Request, Uri}

import scala.annotation.unused


trait MetadataClient {
  def getMetadata(assetId: AssetId): EitherT[IO, MetadataResponseError, MetadataResponse]
}

class HttpMetadataClient(@unused baseUri: Uri, @unused client: Client[IO]) extends MetadataClient {

  override def getMetadata(assetId: AssetId): EitherT[IO, MetadataResponseError, MetadataResponse] = {
//    EitherT(
//      client.run(Request[IO](uri = baseUri / assetId.value / "metadata")).use(r => r.status match {
//        case s if s.isSuccess => r.attemptAs[MetaResponse].leftMap(_ => DecodeFailure).value
//        case NotFound         => IO.pure(Left(MetadataNotFound))
//        case e @ _            => IO.pure(Left(OtherError(e, e.reason)))
//      })
//    )
    ???
  }


}
