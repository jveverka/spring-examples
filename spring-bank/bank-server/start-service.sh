#!/bin/sh

echo "Starting bank-server ..."

while ! nc -z localhost 5432; do
  echo "Waiting for postgresql to start ..."
  sleep 1
done

echo "postgresql is ready, starting bank-server !"
java -Xms32m -Xms128M -jar /bank-server-1.0.0-SNAPSHOT.jar
