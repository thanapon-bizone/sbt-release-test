import sbtrelease.ReleasePlugin.autoImport.{ReleaseStep, releaseProcess, releaseVersion}
import sbtrelease.ReleaseStateTransformations._

ThisBuild / organization := "com.bizone"
ThisBuild / scalaVersion := "2.12.14"

val scalaLibVersion = "2.12"
val projectName = "velocity-lib"

lazy val releaseSteps = Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  setReleaseVersion,
  publishArtifacts,
  tagRelease,
  setNextVersion,
  commitNextVersion,
  pushChanges
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "velocity-lib",
    publishArtifact := false,
    publish / skip := true,
    releaseProcess := releaseSteps,
    releaseNextCommitMessage := s"Setting next version to ${(ThisBuild / version).value}",
    assembly / assemblyJarName := s"${projectName}_${scalaLibVersion}.jar",

  )
  .settings(
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
  )