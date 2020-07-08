# Spring-boot Proxy Server
This demo shows simple spring boot proxy server used to access other REST service intercepting all requests.

![architecture](docs/architecture-proxy.svg)

### Request Routing
Provided ``proxy.target-url=http://localhost:8081/data``  
__GET__ ``http://localhost:8088/proxy/info`` -> __GET__ ``http://localhost:8081/data/info``

### Build and run
Provide your configuration file based on [this](src/main/resources/application.yml) example.
Set ``proxy.target-url`` accordingly.
```
gradle clean build test
java -jar build/libs/spring-proxy-1.0.0-SNAPSHOT.jar --spring.config.location=file:./src/main/resources/application.yml
```

### Build Docker Image
Docker files for x86_64 and ARM armv7l/aarch64 architectures are available. 
This example shows how to build docker image for x86_64 architecture.
```
docker build -t spring-proxy-x86:1.0.0-SNAPSHOT --file Dockerfile.x86_64 .
docker image list
docker save --output="build/spring-proxy-x86:1.0.0-SNAPSHOT.tar" spring-proxy-x86:1.0.0-SNAPSHOT
docker image rm -f <imageid>
docker run -p 8081:8081 spring-proxy-x86:1.0.0-SNAPSHOT
```

### Full stack docker-compose demo
Use docker-compose to run complete stack:
1. spring-proxy on localhost:8088
2. spring-demo on localhost:8081
```
docker-compose up --build -d
curl http://localhost:8081/data/info # <- direct call of target service
curl http://localhost:8088/proxy/info # <- call target service via proxy
docker-compose down -v --rmi all --remove-orphans
```
