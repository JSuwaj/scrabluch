ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "scrabluch",
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
      "com.typesafe.akka" %% "akka-actor" % "2.6.14",
      "com.typesafe.akka" %% "akka-stream" % "2.6.14",
      "com.typesafe.akka" %% "akka-http" % "10.2.4",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.6.14",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.4",
      "org.scalatest" %% "scalatest" % "3.2.9" % "test",
      "com.typesafe.akka" %% "akka-http-testkit" % "10.2.4" % "test",
      "ch.megard" %% "akka-http-cors" % "1.1.1",
    )
  )
