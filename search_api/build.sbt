name := "search_api"
 
version := "1.0"

scalaVersion := "2.11.11"

resolvers += "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases"
resolvers += Resolver.url("Typesafe Ivy releases", url("https://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  guice, specs2 % Test,
  "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.7.2",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "ch.qos.logback" % "logback-core" % "1.1.7",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.21",
  "org.codehaus.janino" % "janino" % "3.0.7",

  // Slick
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",

  // Postgresql
  "org.postgresql" % "postgresql" % "42.2.19"
)

//unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

fork in run := true