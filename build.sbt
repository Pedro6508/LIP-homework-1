ThisBuild / scalaVersion := "3.3.0"
ThisBuild / organization := "com.example"

val toolkitTest = "org.scala-lang" %% "toolkit-test" % "0.2.0"

lazy val TrabalhoLIP = project
  .in(file("."))
  .aggregate(TrabalhoLIPCore)
  .dependsOn(TrabalhoLIPCore)
  .settings(
    name := "TrabalhoLIP",
    libraryDependencies += toolkitTest % Test,
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0"
  )

lazy val TrabalhoLIPCore = project
  .in(file("core"))
  .settings(
    name := "TrabalhoLIP Core",
    libraryDependencies += "org.scala-lang" %% "toolkit" % "0.2.0",
    libraryDependencies += toolkitTest % Test
  )