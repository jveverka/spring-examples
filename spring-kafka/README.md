# Spring kafka demos

* [__kafka-producer__](kafka-producer) - app producing messages.
* [__kafka-consumer__](kafka-consumer) - app consuming messages.
* [__kafka-simple__](kafka-simple) - request-response simple demo.

## Download and Start kafka locally
Download [kafka](https://downloads.apache.org/kafka/2.6.0/kafka_2.13-2.6.0.tgz) and unzip
```
tar -xzf kafka_2.13-2.6.0.tgz
cd kafka_2.13-2.6.0

#start zookeeper and kafka
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties

#create topic service-requests
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic service-requests
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic service-requests
```
