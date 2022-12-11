ThisBuild / organization := "com.bizone"
ThisBuild / scalaVersion := "2.12.14"

val scalaLibVersion = "2.12"
val projectName = "velocity-lib"

lazy val root = project
  .in(file("."))
  .settings(
    name := "velocity-lib",
    publishArtifact := false,
    publish / skip := true,

    assembly / assemblyJarName := s"${projectName}_${scalaLibVersion}.jar",
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,

  )