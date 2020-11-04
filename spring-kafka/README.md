# Spring kafka demo

## Download and Start kafka locally
Download [kafka]() and unzip
```
tar -xzf kafka_2.13-2.6.0.tgz
cd kafka_2.13-2.6.0

#start zookeeper and kafka
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties

#create topic service-requests
kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic service-requests
```
