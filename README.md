[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java17](https://img.shields.io/badge/java-17-blue)](https://img.shields.io/badge/java-17-blue)
[![Gradle](https://img.shields.io/badge/gradle-v7.3-blue)](https://img.shields.io/badge/gradle-v7.3-blue)
![Build and Test](https://github.com/jveverka/spring-examples/workflows/Build%20and%20Test/badge.svg)

# Spring-Boot examples
This project contains various simple or not-so simple [Spring Boot](https://spring.io/projects/spring-boot) examples.

### Environment setup
Minimal requirements: Please make sure following software is installed on your PC.
* [OpenJDK 17](https://adoptium.net/releases.html?variant=openjdk17&jvmVariant=hotspot)
* [Gradle 7.5](https://gradle.org/install/) or later

Please check [full system requirements](docs/system-requirements.md) for more details. 

### Compile and Run
```
gradle clean build test
gradle --build-file spring-api-first/build.gradle clean openApiGenerate build test
```

### Examples
* [__spring native__](spring-native) - Spring native & AOT demo.
* [__spring data__](spring-data) - JPA / Hibernate / spring data and Flyway demo.
* [__spring websocket__](spring-websockets) - simple websocket demo.
* [__spring demo__](spring-demo) - basic springboot application, actuator, buildinfo, swagger.
* [__spring proxy__](spring-proxy) - simple springboot http proxy demo.
* [__spring API first__](spring-api-first) - OpenAPI 3.0 API first application design.
* [__spring webflux__](spring-webflux) - Spring Webflux example.
* [__spring dependency injection__](spring-di) - simple dependency injection demo.
* [__spring jcasbin__](spring-jcasbin) - simple integration example of jcasbin in spring app.
* [__spring security__](spring-security) - cookie session tracking and web security.
* [__spring security-jwt__](spring-security-jwt) - JWT based web security.
* [__spring fileserver__](spring-fileserver) - simple file server ove REST APIs 
* [__spring_mockwebserver__](spring-mockwebserver) - simple http proxy service calling another service.  
* [__spring bank__](spring-bank) - simple transactional web application.
* [__spring_mongo__](spring-mongo) - simple springboot + mongodb with testcontainers example.

### Topics
* [__Spring Native__](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/) - [Spring native case study.](spring-native)
* [__Reactive Spring__](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html) - [Reactive Spring case study.](spring-webflux)
* [__JUnit5__](https://github.com/junit-team/junit5/) - [all projects]()
* __Security__ - [__spring security__](spring-security), [__spring jcasbin__](spring-jcasbin), [__spring security-jwt__](spring-security-jwt), [__spring-webflux__](spring-webflux)
* __Web/Http__ - [__spring proxy__](spring-proxy), [__spring fileserver__](spring-fileserver), [__spring websocket__](spring-websockets), [__spring API first__](spring-api-first), [__spring-webflux__](spring-webflux)
* [__Spring Data__](https://spring.io/projects/spring-data) - [__spring data__](spring-data), [__spring bank__](spring-bank)
* __Integrations__ - [__spring proxy__](spring-proxy), [__spring API first__](spring-api-first)

_Enjoy !_
