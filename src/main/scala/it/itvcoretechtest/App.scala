package it.itvcoretechtest

import cats.effect.IO
import it.itvcoretechtest.http.MetadataClient


trait CustomErrorType
case object AssetNotFound extends CustomErrorType

case class TimestampImage()

trait App {
  def run: IO[Either[CustomErrorType, Unit]]

}

class ThumbnailApp(client: MetadataClient) extends App {


  override def run: IO[Either[CustomErrorType, Unit]] = ???



}
