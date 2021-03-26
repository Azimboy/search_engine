start .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

start .\bin\windows\kafka-server-start.bat .\config\server.properties

# Create topics
.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TestTopic
.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic NewTopic

# List topics
.\bin\windows\kafka-topics.bat --list --zookeeper localhost:2181

# Send messages to topic
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic TestTopic

# Show messages in topic
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic TestTopic --from-beginning