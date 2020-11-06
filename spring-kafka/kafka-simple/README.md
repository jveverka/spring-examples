# Spring kafka demo

## REST endpoints
* __POST__ ``/services/send-message`` - send message, fire and forget.
* __POST__ ``/services/send-message-and-get-response`` - send message, and wait for reply.

### message
```
{
  "message": "your-message-here"
}
``` 

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
```
