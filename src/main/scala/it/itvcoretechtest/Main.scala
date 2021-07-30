package it.itvcoretechtest

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    ItvcoretechtestServer.stream[IO].compile.drain.as(ExitCode.Success)
}
