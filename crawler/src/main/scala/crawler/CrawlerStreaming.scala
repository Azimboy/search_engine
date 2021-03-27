package crawler

import java.nio.charset.StandardCharsets
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
//    properties.put("serializer.class", "kafka.serializer.StringEncoder");
//    properties.put("request.required.acks", "1");
//    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
//    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
//    properties.put("value.serializer.encoding", "ISO-8859-9");

    val dataStream = env.addSource(new FlinkKafkaConsumer[String](
      java.util.regex.Pattern.compile(config.topic),
      new SimpleStringSchema,
      properties))

    val jdbcOutput = JDBCOutputFormat.buildJDBCOutputFormat()
      .setDrivername("org.postgresql.Driver")
      .setDBUrl(config.databaseUrl)
      .setUsername(config.postgresUsername)
      .setPassword(config.postgresPassword)
      .setQuery("INSERT INTO public.activities (text) VALUES (?)")
      .setBatchInterval(1)
      .finish()

    val rows = dataStream.map {text =>
      val bytes = text.getBytes("UTF-8")
      val value = new String(bytes, "UTF-8")
//      val fixed = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)
//      val fixed = new String(text.getBytes("Windows-1252"), "UTF-8")
      val row = new Row(1)
      row.setField(0, value)
      row
    }
    rows.print()
    rows.writeUsingOutputFormat(jdbcOutput)
      .name("Write to postgres")

    env.execute()
  }
}