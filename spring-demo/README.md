# Simple Spring Boot demo
This is simple spring-boot application demo. 

### Rest Endpoints
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
  
### Swagger API docs
* __GET__ http://localhost:8081/v2/api-docs
* __Swagger2 UI__ - http://localhost:8081/swagger-ui.html

## Static Resources
* __GET__ http://localhost:8081/static/inex.html

### Build and run
```
gradle clean build test
java -jar build/libs/spring-demo-1.0.0-SNAPSHOT.jar
```

### Build Docker Image
Docker images for x86-64 and ARM platform are ready. This example shows how to build x86-64 compatible docker image.
```
docker build -t spring-demo-x86:1.0.0-SNAPSHOT --file Dockerfile.x86_64 .
docker image list
docker save --output="build/spring-demo-x86:1.0.0-SNAPSHOT.tar" spring-demo-x86:1.0.0-SNAPSHOT
docker image rm -f <imageid>
docker run -p 8081:8081 spring-demo-x86:1.0.0-SNAPSHOT
```
