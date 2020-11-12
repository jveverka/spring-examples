# Kafka Producer - Consumer demo

![kafka-topics](kafka-topics.svg)

* __Partitioning:__ Kafka can guarantee ordering only inside the same 
  partition and it is therefore important to be able to route correlated 
  messages into the same partition. To do so you need to specify a key for 
  each message and Kafka will put all messages with the same key in the 
  same partition.
* The messages with the same key are guaranteed to be written to the same partition.