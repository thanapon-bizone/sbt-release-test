ThisBuild / organization := "com.bizone"
ThisBuild / scalaVersion := "2.12.14"

val scalaLibVersion = "2.12"
val sparkVersion = "3.2.1"
val projectName = "velocity-lib"

lazy val root = project
  .in(file("."))
  .settings(
    name := projectName,
    publishArtifact := false,
    publish / skip := true,

  )
  .settings(
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,

    // libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion,
    // libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion,

    libraryDependencies += "org.apache.avro" % "avro-mapred" % "1.10.2",
    libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion,
    libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion,
    libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion,
    libraryDependencies += "org.apache.spark" %% "spark-avro" % sparkVersion ,

    libraryDependencies += "com.microsoft.azure" % "azure-sqldb-spark" % "1.0.2",
    libraryDependencies += "com.microsoft.azure" % "adal4j" % "1.6.7",
    libraryDependencies += "com.microsoft.azure" %% "spark-mssql-connector" % "1.2.0",
    libraryDependencies += "com.azure" % "azure-messaging-eventgrid" % "4.12.1",

    libraryDependencies += "com.databricks" %% "dbutils-api" % "0.0.6",
    libraryDependencies += "io.delta" %% "delta-core" % "2.2.0",
  )