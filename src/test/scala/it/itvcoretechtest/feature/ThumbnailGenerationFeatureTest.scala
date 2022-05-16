package it.itvcoretechtest.feature

import it.itvcoretechtest._
import it.itvcoretechtest.checksum.{Md5CalculationFailure, MetadataNotFound}
import it.itvcoretechtest.service.{InvalidChecksum, TimestampOutOfBounds}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ThumbnailGenerationFeatureTest extends AnyFunSuite with Matchers {

  // unable to fetch checksum
  // unable to calculate checksum
  // mismatching checksum
  // thumbnail generation failure - invalid timestamp


  val invalidAssetId: VideoAssetId = VideoAssetId("invalidAssetId")
  val validAssetId: VideoAssetId = VideoAssetId("invalidAssetId")

  val invalidFilePath: Filepath = Filepath("invalidFilepath")
  val validFilePath: Filepath = Filepath("validFilepath")
  val otherFilePath: Filepath = Filepath("otherFilepath")

  val invalidThumbnailTimestamp = ThumbnailTimestamp(100)
  val validThumbnailTimestamp = ThumbnailTimestamp(5)


  test("The app will return the appropriate error when unable to fetch the checksum") {
    val appState = AppState()

    withTestApp(appState) { testApp =>
      testApp.generateThumbnail(invalidAssetId, validThumbnailTimestamp, validFilePath).value.map{ result =>
        result shouldBe Left(MetadataNotFound)
      }
    }

  }


  test("The app will return the appropriate error when unable to calculate the checksum") {
    val appState = AppState()

    withTestApp(appState) { testApp =>
      testApp.generateThumbnail(validAssetId, validThumbnailTimestamp, invalidFilePath).value.map{ result =>
        result shouldBe Left(Md5CalculationFailure)
      }
    }

  }


  test("The app will return the appropriate error when the calcualted and fetched checksums don't match") {
    val appState = AppState()

    withTestApp(appState) { testApp =>
      testApp.generateThumbnail(validAssetId, validThumbnailTimestamp, otherFilePath).value.map{ result =>
        result shouldBe Left(InvalidChecksum)
      }
    }

  }


  test("The app will return the appropriate error when unable to generate a thumbnail") {
    val appState = AppState()

    withTestApp(appState) { testApp =>
      testApp.generateThumbnail(validAssetId, invalidThumbnailTimestamp, validFilePath).value.map{ result =>
        result shouldBe Left(TimestampOutOfBounds)
      }
    }
  }

  test("The app will successfully generate a thumbnail if everything is valud") {
    val appState = AppState()

    withTestApp(appState) { testApp =>
      testApp.generateThumbnail(validAssetId, invalidThumbnailTimestamp, validFilePath).value.map{ result =>
        result shouldBe Right(())
      }
    }
  }



}
