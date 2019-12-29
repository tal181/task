import sbt._

val sparkVersion = "2.4.0"

val log4jVersion = "2.11.0"


lazy val root = (project in file("."))
  .settings(
    organization in ThisBuild := "com.example",
    scalaVersion in ThisBuild := "2.11.0",
    version      in ThisBuild := "0.1.0-SNAPSHOT",
    name := "task",
    aggregationDependencies
  )

lazy val aggregationDependencies = libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.2" excludeAll ExclusionRule(organization = "com.fasterxml.jackson"),
  "org.rogach" %% "scallop" % "3.1.2" excludeAll ExclusionRule(organization = "com.fasterxml.jackson"),
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion

)

