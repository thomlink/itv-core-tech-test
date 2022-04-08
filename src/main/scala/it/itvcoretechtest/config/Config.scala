package it.itvcoretechtest.config

import cats.effect.IO
import ciris.env
import org.http4s.Uri
case class Config(itvBaseUri: Uri)

object Config {

  val baseUri = env("AIS_BASE_URL").as[Uri]

  def loadConfig: IO[Config] = baseUri.map(Config(_)).load[IO]


}
