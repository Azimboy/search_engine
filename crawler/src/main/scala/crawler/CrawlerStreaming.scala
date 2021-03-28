package crawler

import java.util.Properties

import org.apache.flink.api.java.io.jdbc.JDBCOutputFormat
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
import org.apache.flink.types.Row

object CrawlerStreaming {

  def main(args: Array[String]): Unit = {
    val config = CrawlerConfig(args)
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", config.bootstrapServers)
    properties.setProperty("zookeeper.connect", config.zookeeperConnect)
    properties.setProperty("group.id", config.groupId)
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.serializer.encoding", "ISO-8859-9");

    val dataStream = env.addSource(new FlinkKafkaConsumer[String](
      config.topic,
      new SimpleStringSchema,
      properties).setStartFromEarliest())

    val jdbcOutput = JDBCOutputFormat.buildJDBCOutputFormat()
      .setDrivername("org.postgresql.Driver")
      .setDBUrl(config.databaseUrl)
      .setUsername(config.postgresUsername)
      .setPassword(config.postgresPassword)
      .setQuery("INSERT INTO public.activities (text) VALUES (?)")
      .setBatchInterval(1)
      .finish()

    val rows = dataStream.filter(text => text.trim.nonEmpty).map {text =>
      val row = new Row(1)
      row.setField(0, text.toLowerCase)
      row
    }
    rows.print()
    rows.writeUsingOutputFormat(jdbcOutput)
      .name("Write to postgres")

    env.execute()
  }
}