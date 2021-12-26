ARG ARCH="amd64"
FROM ${ARCH}/eclipse-temurin:17-jdk-focal
COPY build/libs/bank-server-1.0.0-SNAPSHOT.jar /bank-server-1.0.0-SNAPSHOT.jar
COPY start-service.sh /start-service.sh
RUN chmod +x /start-service.sh
ENTRYPOINT ["/start-service.sh"]
