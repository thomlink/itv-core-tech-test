package it.itvcoretechtest

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    for {
    _ <- if (args.length != 3)
      IO.raiseError(new IllegalArgumentException("""Usage: sbt run "<filepath> <timestamp> <thumbnailFile>"""")
    ) else
      IO.unit

    thumbnailService = new ThumbnailApp(???)

    _ <- thumbnailService.run

  } yield ExitCode.Success
}
