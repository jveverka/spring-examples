#!/bin/bash

WORK_DIR=`pwd`
echo "WORK_DIR=${WORK_DIR}"

if [ -f "kafka_2.13-2.6.0.tgz" ]; then
  echo "kafka package already downloaded, skipping ..."
else 	
  wget https://downloads.apache.org/kafka/2.6.0/kafka_2.13-2.6.0.tgz
fi

tar xzvf kafka_2.13-2.6.0.tgz

mkdir -p kafka_2.13-2.6.0/data/zookeeper1
mkdir -p kafka_2.13-2.6.0/data/zookeeper2
mkdir -p kafka_2.13-2.6.0/data/zookeeper3

echo 1 > kafka_2.13-2.6.0/data/zookeeper1/myid
echo 2 > kafka_2.13-2.6.0/data/zookeeper2/myid
echo 3 > kafka_2.13-2.6.0/data/zookeeper3/myid

mkdir -p kafka_2.13-2.6.0/data/kafka1
mkdir -p kafka_2.13-2.6.0/data/kafka2
mkdir -p kafka_2.13-2.6.0/data/kafka3

cp kafka_2.13-2.6.0/config/server.properties  kafka_2.13-2.6.0/config/server1.properties
cp kafka_2.13-2.6.0/config/server.properties  kafka_2.13-2.6.0/config/server2.properties
cp kafka_2.13-2.6.0/config/server.properties  kafka_2.13-2.6.0/config/server3.properties

cp kafka_2.13-2.6.0/config/zookeeper.properties  kafka_2.13-2.6.0/config/zookeeper1.properties
cp kafka_2.13-2.6.0/config/zookeeper.properties  kafka_2.13-2.6.0/config/zookeeper2.properties
cp kafka_2.13-2.6.0/config/zookeeper.properties  kafka_2.13-2.6.0/config/zookeeper3.properties

sed -ie "s/^clientPort=.*/clientPort=2181/" kafka_2.13-2.6.0/config/zookeeper1.properties
sed -ie "s/^clientPort=.*/clientPort=2182/" kafka_2.13-2.6.0/config/zookeeper2.properties
sed -ie "s/^clientPort=.*/clientPort=2183/" kafka_2.13-2.6.0/config/zookeeper3.properties

NORMALIZED_PATH=`echo ${WORK_DIR}/kafka_2.13-2.6.0/data/zookeeper1 | sed -e "s/\//\\\\\\\\\//g"` 
sed -ie "s/^dataDir=.*/dataDir=${NORMALIZED_PATH}/" kafka_2.13-2.6.0/config/zookeeper1.properties
NORMALIZED_PATH=`echo ${WORK_DIR}/kafka_2.13-2.6.0/data/zookeeper2 | sed -e "s/\//\\\\\\\\\//g"`
sed -ie "s/^dataDir=.*/dataDir=${NORMALIZED_PATH}/" kafka_2.13-2.6.0/config/zookeeper2.properties
NORMALIZED_PATH=`echo ${WORK_DIR}/kafka_2.13-2.6.0/data/zookeeper3 | sed -e "s/\//\\\\\\\\\//g"`
sed -ie "s/^dataDir=.*/dataDir=${NORMALIZED_PATH}/" kafka_2.13-2.6.0/config/zookeeper3.properties

echo "" >> kafka_2.13-2.6.0/config/zookeeper1.properties
echo "tickTime=2000" >> kafka_2.13-2.6.0/config/zookeeper1.properties
echo "initLimit=5" >> kafka_2.13-2.6.0/config/zookeeper1.properties
echo "syncLimit=2" >> kafka_2.13-2.6.0/config/zookeeper1.properties
echo "server.1=localhost:2666:3666" >> kafka_2.13-2.6.0/config/zookeeper1.properties
echo "server.2=localhost:2667:3667" >> kafka_2.13-2.6.0/config/zookeeper1.properties
echo "server.3=localhost:2668:3668" >> kafka_2.13-2.6.0/config/zookeeper1.properties

echo "" >> kafka_2.13-2.6.0/config/zookeeper2.properties
echo "tickTime=2000" >> kafka_2.13-2.6.0/config/zookeeper2.properties
echo "initLimit=5" >> kafka_2.13-2.6.0/config/zookeeper2.properties
echo "syncLimit=2" >> kafka_2.13-2.6.0/config/zookeeper2.properties
echo "server.1=localhost:2666:3666" >> kafka_2.13-2.6.0/config/zookeeper2.properties
echo "server.2=localhost:2667:3667" >> kafka_2.13-2.6.0/config/zookeeper2.properties
echo "server.3=localhost:2668:3668" >> kafka_2.13-2.6.0/config/zookeeper2.properties

echo "" >> kafka_2.13-2.6.0/config/zookeeper3.properties
echo "tickTime=2000" >> kafka_2.13-2.6.0/config/zookeeper3.properties
echo "initLimit=5" >> kafka_2.13-2.6.0/config/zookeeper3.properties
echo "syncLimit=2" >> kafka_2.13-2.6.0/config/zookeeper3.properties
echo "server.1=localhost:2666:3666" >> kafka_2.13-2.6.0/config/zookeeper3.properties
echo "server.2=localhost:2667:3667" >> kafka_2.13-2.6.0/config/zookeeper3.properties
echo "server.3=localhost:2668:3668" >> kafka_2.13-2.6.0/config/zookeeper3.properties


