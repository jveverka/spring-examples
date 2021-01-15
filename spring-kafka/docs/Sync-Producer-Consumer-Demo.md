# Kafka Producer - Consumer Sync demo

## Simple deployment
![kafka-sync-demo](kafka-sync-demo.svg)

### Create Kafka Topics
```
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic sync-prod-con-requests
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic sync-prod-con-responses
```

### Start producer 
```
java -jar build/libs/kafka-sync-producer-1.0.0-SNAPSHOT.jar
```

### Start consumer
```
java -jar build/libs/kafka-sync-consumer-1.0.0-SNAPSHOT.jar
```

## Cluster deployment

## REST APIs
### Send Message
```
curl --location --request POST 'http://localhost:8082/services/messages' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "id-001",
    "message": "hi"
}'
```