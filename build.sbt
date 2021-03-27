import sbt.Keys._

name := "search_engine"
 
version := "1.0"

scalaVersion := "2.11.11"

resolvers += Resolver.url("Typesafe Ivy releases", url("https://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)

lazy val `search_engine` = (project in file("."))
  .aggregate(`crawler`, `search_api`)

lazy val `crawler` = project in file("crawler")

lazy val `search_api` = (project in file("search_api"))
  .dependsOn(crawler)
  .enablePlugins(PlayScala)
  .enablePlugins(SbtWeb)

val runCrawler = inputKey[Unit]("Runs crawler ...")
val runSearchApi = inputKey[Unit]("Runs search_api ...")

runCrawler := {
  (run in Compile in `crawler`).evaluated
}

runSearchApi := {
  (run in Compile in `search_api`).evaluated
}

fork in run := true