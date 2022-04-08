package it.itvcoretechtest

import ciris.ConfigDecoder
import org.http4s.Uri

package object config {

  implicit final val UriConfigDecoder: ConfigDecoder[String, Uri] =
    ConfigDecoder[String].mapOption("Uri")(u => Uri.fromString(u).toOption)
}
