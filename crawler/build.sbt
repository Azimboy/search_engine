name := "crawler"

version := "1.0"

scalaVersion := "2.11.11"

libraryDependencies ++= {
  Seq(
    "org.apache.flink" %% "flink-connector-kafka" % "1.12.0",
    "org.apache.flink" %% "flink-streaming-scala" % "1.12.0",

    "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "ch.qos.logback" % "logback-core" % "1.1.7",
    "org.slf4j" % "log4j-over-slf4j" % "1.7.21"
  )
}
