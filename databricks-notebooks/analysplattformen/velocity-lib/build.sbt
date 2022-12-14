import sbtrelease.ReleasePlugin.autoImport.{ReleaseStep, releaseProcess, releaseVersion}
import sbtrelease.ReleaseStateTransformations._

ThisBuild / organization := "com.bizone"
ThisBuild / scalaVersion := "2.12.14"

val scalaLibVersion = "2.12"
val projectName = "velocity-lib"
// val versionNextRelease = taskKey[String=>String]


lazy val releaseSteps = Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  setReleaseVersion,
  publishArtifacts,
  tagRelease
)

lazy val releaseSteps2 = Seq[ReleaseStep](
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

    assembly / assemblyJarName := s"${projectName}_${scalaLibVersion}.jar",
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,

    // releaseProcess := Seq[ReleaseStep](
    //   releaseSteps
    // )
    // releaseVersion := "v0.9.0",
    // releaseProcess := {
    //   releaseSteps2
    // }

  )