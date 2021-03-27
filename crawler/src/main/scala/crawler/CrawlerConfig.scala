package crawler

import scopt.OptionParser

case class CrawlerConfig(
  topic: String = "default",
  groupId: String = "default",
  zookeeperConnect: String = "localhost:2181",
  bootstrapServers: String = "localhost:9092",
  postgresHost: String = "localhost:5432",
  postgresDatabase: String = "postgres",
  postgresUsername: String = "postgres",
  postgresPassword: String = "postgres"
) {
  val databaseUrl = s"jdbc:postgresql://$postgresHost/$postgresDatabase"
}

object CrawlerConfig {

  private val parser = new OptionParser[CrawlerConfig]("CrawlerConfig") {
    override def errorOnUnknownArgument: Boolean = false

    head("CrawlerConfig")

    opt[String]("topic").required().valueName("topic")
      .text("Topic")
      .action((v, c) => c.copy(topic = v))

    opt[String]("group_id").required().valueName("group_id")
      .text("Group ID")
      .action((v, c) => c.copy(groupId = v))

    opt[String]("zookeeper_connect").required().valueName("zookeeper_connect")
      .text("Zookeeper Connect")
      .action((v, c) => c.copy(zookeeperConnect = v))

    opt[String]("bootstrap_servers").required().valueName("bootstrap_servers")
      .text("Bootstrap Servers")
      .action((v, c) => c.copy(bootstrapServers = v))

    opt[String]("postgres_host").required().valueName("postgres_host")
      .text("Postgres Host")
      .action((v, c) => c.copy(postgresHost = v))

    opt[String]("postgres_database").required().valueName("postgres_database")
      .text("Postgres Database")
      .action((v, c) => c.copy(postgresDatabase = v))

    opt[String]("postgres_user").required().valueName("postgres_user")
      .text("Postgres User")
      .action((v, c) => c.copy(postgresUsername = v))

    opt[String]("postgres_password").required().valueName("postgres_password")
      .text("Postgres Password")
      .action((v, c) => c.copy(postgresPassword = v))
  }

  def apply(args: Array[String]): CrawlerConfig = {
    parser.parse(args, CrawlerConfig()) match {
      case Some(config) => config
      case None => sys.error("Could not parse arguments")
    }
  }

}