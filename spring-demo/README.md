# Simple Spring Boot demo
This is simple spring-boot application demo. 
[Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth#overview)
is enabled in this demo to improve log traceability.

### Rest Endpoints
* __GET__ http://localhost:8081/data/build-info
* __GET__ http://localhost:8081/data/info
* __POST__ http://localhost:8081/data/message 
  ```
  { "data": "message" }
  ```

* REST endpoint handling generic data payloads  
  __POST__ http://localhost:8081/data/generics
  ```
  {"name":"simpleData","data":{"@class":"itx.examples.springboot.demo.dto.generic.SimpleDataPayload","simpleData":"simple"}}
  ```
  ```
  {"name":"complexData","data":{"@class":"itx.examples.springboot.demo.dto.generic.ComplexDataPayload","complexData":"complex"}}
  ```
* Returns information about incoming HTTP request as JSON response. This endpoint should be used for testing purposes.
  All http methods are supported.      
  __ALL__ http://localhost:8081/data/test 
  
### OpenAPI v3 docs and Swagger UI
* __GET__ http://localhost:8081/v3/api-docs
* __GET__ http://localhost:8081/v3/api-docs.yaml
* __Swagger UI__ - http://localhost:8081/swagger-ui/index.html?url=/v3/api-docs

## Actuator endpoints
* __GET__ http://localhost:8081/actuator

## Static Resources
* __GET__ http://localhost:8081/static/index.html

### Build and run
```
gradle clean build test
java -jar build/libs/spring-demo-1.0.0-SNAPSHOT.jar
```

### Build Docker Image
Docker files for x86_64 and ARM aarch64 architectures are available. 
This example shows how to build docker image for x86_64 architecture.
```
export ARCH=amd64
export VERSION=1.0.4
#export ARCH=arm64v8
docker build -t jurajveverka/spring-demo:${VERSION} .
docker push jurajveverka/spring-demo:${VERSION}
docker run -p 8081:8081 -d jurajveverka/spring-demo:${VERSION} -n spring-demo
docker exec -ti spring-demo /bin/bash
```
### Run with Docker
```
docker run -p 8081:8081 jurajveverka/spring-demo:${VERSION}
```

### Run with Docker Swarm
```
sudo docker stack deploy -c spring-demo-swarm.yml spring-demo
sudo docker stack services spring-demo
sudo docker stack rm spring-demo
```

### LogBack configuration
* [logback.xml](src/main/resources/logback.xml) - custom logger configuration.
* [LogUtils](src/main/java/itx/examples/springboot/demo/logs/LogUtils.java) - how to log custom data.
 