name := "search_engine"
 
version := "1.0" 
      
lazy val `search_engine` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  guice, specs2 % Test,
  "org.apache.flink" %% "flink-connector-kafka" % "1.12.0",
  "org.apache.flink" %% "flink-streaming-scala" % "1.12.0" ,

  "com.typesafe.scala-logging" % "scala-logging_2.12" % "3.7.2",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "ch.qos.logback" % "logback-core" % "1.1.7",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.21",
  "org.codehaus.janino" % "janino" % "3.0.7",

  // Slick
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",

  // Postgresql
  "org.postgresql" % "postgresql" % "42.2.19",

  "com.typesafe.akka" %% "akka-protobuf" % "2.6.10"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      