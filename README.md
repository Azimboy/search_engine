# Mini Search Engine with Kafka and Flink

#### Start ZooKeeper server on Windows:

```
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

#### Start Kafka server on Windows:

```
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

#### Create topic
```
.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TestTopic
```

#### Send messages to topic 
```
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic TestTopic
```

### Running Crawler Engine
```
sbt "runCrawler --topic TestTopic --group_id TestGroup --zookeeper_connect localhost:2181 --bootstrap_servers localhost:9092 --postgres_host loc
alhost:5432 --postgres_database search_db --postgres_user admin --postgres_password 123"
```

### Running Search API
```
sbt runSearchApi
```