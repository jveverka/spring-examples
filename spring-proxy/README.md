# Spring-boot Proxy Server
This demo shows simple spring boot proxy server used to access other REST service intercepting all requests.

![architecture](docs/architecture-proxy.svg)

### Build and run
Provide your configuration file based on [this](src/main/resources/application.yml) example.
Set ``proxy.target-url`` accordingly.
```
gradle clean build test
java -jar build/libs/spring-proxy-1.0.0-SNAPSHOT.jar --spring.config.location=file:./src/main/resources/application.yml
```
### Request Routing
Provided ``proxy.target-url=http://localhost:8081/data``  
__GET__ ``http://localhost:8088/proxy/info`` -> __GET__ ``http://localhost:8081/data/info``