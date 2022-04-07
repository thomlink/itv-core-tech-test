val Http4sVersion = "0.21.24"
val CirceVersion = "0.13.0"
val MunitVersion = "0.7.20"
val LogbackVersion = "1.2.3"
val MunitCatsEffectVersion = "0.13.0"
val ScalatestVersion = "3.2.3"
val ScalacheckVersion = "1.15.3"


lazy val root = (project in file("."))
  .settings(
    organization := "it",
    name := "itv-core-tech-test",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "org.scalameta"   %% "munit"               % MunitVersion           % Test,
      "org.typelevel"   %% "munit-cats-effect-2" % MunitCatsEffectVersion % Test,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      "org.scalameta"   %% "svm-subs"            % "20.2.0",
      "org.scalatest" %% "scalatest" % ScalatestVersion % "it, test",
      "org.scalatestplus" %% "scalacheck-1-15" % "3.2.3.0" % "it, test",
      "org.scalacheck" %% "scalacheck" % ScalacheckVersion % "it, test",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.0" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
    testFrameworks += new TestFramework("munit.Framework")
  )
