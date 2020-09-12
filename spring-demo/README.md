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
  
### OpenAPI v3 docs and Swagger UI
* __GET__ http://localhost:8081/v3/api-docs
* __GET__ http://localhost:8080/v3/api-docs.yaml
* __Swagger2 UI__ - http://localhost:8081/swagger-ui/index.html?url=/v3/api-docs

## Static Resources
* __GET__ http://localhost:8081/static/inex.html

### Build and run
```
gradle clean build test
java -jar build/libs/spring-demo-1.0.0-SNAPSHOT.jar
```

### Build Docker Image
Docker files for x86_64 and ARM armv7l/aarch64 architectures are available. 
This example shows how to build docker image for x86_64 architecture.
```
docker build -t spring-demo-x86:1.0.0-SNAPSHOT --file Dockerfile.x86_64 .
docker image list
docker save --output="build/spring-demo-x86:1.0.0-SNAPSHOT.tar" spring-demo-x86:1.0.0-SNAPSHOT
docker image rm -f <imageid>
docker run -p 8081:8081 spring-demo-x86:1.0.0-SNAPSHOT
```
