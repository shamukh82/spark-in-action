import AssemblyKeys._
assemblySettings

name := "spark"

version := "1.0"

scalaVersion := "2.11.8"

val sparkVersion = "2.2.0"


resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided"
)

lazy val buildSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.practice.spark",
  scalaVersion := scalaVersion.value
)

val app = (project in file("app")).
  settings(buildSettings: _*).
  settings(assemblySettings: _*).
  settings(
    // your settings here
  )

jarName in assembly := "spark-in-action-practice.jar"