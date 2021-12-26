ARG ARCH="amd64"
FROM ${ARCH}/eclipse-temurin:17-jdk-focal
COPY build/libs/spring-proxy-1.0.0-SNAPSHOT.jar /spring-proxy-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xms32m", "-Xms128M", "-jar","/spring-proxy-1.0.0-SNAPSHOT.jar"]
