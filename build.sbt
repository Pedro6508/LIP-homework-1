ThisBuild / scalaVersion := "3.3.0"
ThisBuild / organization := "com.example"

val toolkitTest = "org.scala-lang" %% "toolkit-test" % "0.2.0"

lazy val TrabalhoLIP = project
  .in(file("."))
  .aggregate(TrabalhoLIPCore)
  .dependsOn(TrabalhoLIPCore)
  .settings(
    name := "TrabalhoLIP",
    libraryDependencies += "org.scala-lang" %% "toolkit-test" % "0.1.7" % Test,
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15",
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.10.0"
  )

lazy val TrabalhoLIPCore = project
  .in(file("core"))
  .settings(
    name := "TrabalhoLIP Core",
    libraryDependencies += "org.scala-lang" %% "toolkit-test" % "0.1.7" % Test,
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.4.0",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.7",
  )