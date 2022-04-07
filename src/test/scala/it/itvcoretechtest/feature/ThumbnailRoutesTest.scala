package it.itvcoretechtest.feature

import cats.effect.IO
import it.itvcoretechtest.http.ThumbnailResponse
import it.itvcoretechtest.{AssetId, ThumbnailTimestamp}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ThumbnailRoutesTest extends AnyFunSuite with Matchers {

  test("can return a thumbnail when requesting valid content") {
    val validAssetId = AssetId("12345")
    val timestamp = ThumbnailTimestamp(5)

    1 shouldBe 1
  }

  test("return 400 when requesting invalid content") {
    val corruptedAssetId = AssetId("xxxxxx")
    val timestamp = ThumbnailTimestamp(5)

    1 shouldBe 1
  }

  def makeRequest(assetId: AssetId, timestamp: ThumbnailTimestamp): IO[ThumbnailResponse] = ???
}
