# Mini Search Engine with Kafka and Flink

## Development Dependencies

- Scala
- Sbt
- Kafka
- Flink
- PostgreSQL

### Running Search API
In PostgreSQL create database and user:
```
postgres=# drop DATABASE crawler;
DROP DATABASE
postgres=# CREATE DATABASE crawler;
CREATE DATABASE
postgres=# CREATE USER crawler WITH PASSWORD '123';
CREATE ROLE
postgres=# ALTER USER crawler WITH SUPERUSER;
ALTER ROLE
postgres=# GRANT ALL PRIVILEGES ON DATABASE crawler TO crawler;
GRANT
```

### Run Search API
```shell script
sbt runSearchApi
```
After successfully starting the service. Request the URL [http: // localhost: 9000 /] from your browser and click the "Apply this script now!"

### Start ZooKeeper:
```shell script
./bin/zookeeper-server-start.sh config/zookeeper.properties
```

### Start Kafka:
```shell script
./bin/kafka-server-start.sh ./config/server.properties
```

### Create topic
```shell script
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test-topic
```

### Send messages to topic 
```shell script
./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test-topic
```
And send following messages to topic:
>Сэр
>Джонс
>Ваша
>карта
>бита
>привет
>как жизнь
>

### Running Crawler Engine
This command starts the crawler service to read messages from the Kafka topic via Flink and writes the messages to PostgreSQL.
```shell script
sbt "runCrawler --topic test-topic --group_id test --zookeeper_connect localhost:2181 --bootstrap_servers localhost:9092 --postgres_host localhost:5432 --postgres_database crawler --postgres_user crawler --postgres_password 123"
```

### Usage Search API
This service returns the first 10 similar words stored by the crawler, in descending order.
Query:
```shell script
curl http://localhost:9000/?word=джакарта
```
Response:
```
карта
джонс
бита
ваша
как жизнь
сэр
привет
```