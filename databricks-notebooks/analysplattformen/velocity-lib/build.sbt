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
    // libraryDependencies += "com.microsoft.azure" %% "azure-sqldb-spark" % "1.0.2",
    // libraryDependencies += "com.microsoft.azure" %% "adal4j" % "1.6.7",
    // libraryDependencies += "com.microsoft.azure" %% "spark-mssql-connector_2.12" % "1.2.0",
    // libraryDependencies += "com.azure" %% "azure-messaging-eventgrid" % "4.12.1"
  )