val scala3Version = "2.12.14"
val sparkVersion = "3.2.1"


lazy val root = project
  .in(file("."))
  .settings(
    name := "Scala 3 Project Template",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,
    libraryDependencies += "org.apache.spark" %% "spark-core" % "3.2.1",
    libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.1",
    // libraryDependencies += "org.apache.spark" % "spark-sql" % sparkVersion,
  )
