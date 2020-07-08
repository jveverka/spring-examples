# Compatible with Architecture: x86_64 systems (Intel, AMD)
FROM adoptopenjdk/openjdk11:jre-11.0.7_10-alpine
COPY build/libs/bank-server-1.0.0-SNAPSHOT.jar /bank-server-1.0.0-SNAPSHOT.jar
COPY start-service.sh /start-service.sh
RUN chmod +x /start-service.sh
ENTRYPOINT ["/start-service.sh"]
