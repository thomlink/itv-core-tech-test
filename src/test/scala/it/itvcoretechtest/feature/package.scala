package it.itvcoretechtest

import cats.effect._
import cats.effect.unsafe.IORuntime
import it.itvcoretechtest.feature.test.{StubChecksumClient, StubThumbnailGenerator}
import it.itvcoretechtest.service.ChecksumVerifierService
import org.scalatest.Assertion

package object feature {
  case class AppState()

  object TestApp {
    def resource(appState: AppState): Resource[IO, ThumbnailApp] = Resource.pure(new ThumbnailApp(
      new ChecksumVerifierService(new StubChecksumClient(appState)),
      new StubThumbnailGenerator(appState)
    ))
  }

//  implicit val ec: ExecutionContext = ExecutionContext.global
//  implicit val cs: ContextShift[IO] = IO.contextShift(ec)
//  implicit val timer: Timer[IO]     = IO.timer(ec)

  implicit val runtime: IORuntime = IORuntime.global

  def withTestApp(appState: AppState = AppState())(runTest: ThumbnailApp => IO[Assertion]): Assertion =
    TestApp.resource(appState).use(runTest).unsafeRunSync()
}
