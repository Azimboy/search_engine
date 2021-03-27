name := "crawler"

version := "1.0"

scalaVersion := "2.11.11"

val flinkVersion = "1.12.2"

libraryDependencies ++= {
  Seq(
    "org.apache.flink" %% "flink-clients" % flinkVersion,
    "org.apache.flink" %% "flink-scala" % flinkVersion,
    "org.apache.flink" %% "flink-streaming-scala" % flinkVersion,
    "org.apache.flink" %% "flink-connector-kafka" % flinkVersion,
    "org.apache.flink" %% "flink-connector-jdbc" % flinkVersion,
    "org.apache.flink" %% "flink-jdbc" % "1.10.3",

    "com.github.scopt" %% "scopt" % "4.0.1",

    "org.postgresql" % "postgresql" % "42.2.19",

    "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "ch.qos.logback" % "logback-core" % "1.1.7",
    "org.slf4j" % "log4j-over-slf4j" % "1.7.21"
  )
}
