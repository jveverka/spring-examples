[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java11](https://img.shields.io/badge/java-11-blue)](https://img.shields.io/badge/java-11-blue)
[![Gradle](https://img.shields.io/badge/gradle-v6.5-blue)](https://img.shields.io/badge/gradle-v6.5-blue)
[![Build Status](https://travis-ci.com/jveverka/spring-examples.svg?branch=master)](https://travis-ci.com/jveverka/spring-examples)

# Spring-Boot demos
This project contains various not-so simple [spring-boot](https://spring.io/projects/spring-boot) demos.

### Environment setup
Minimal requirements: Please make sure following software is installed on your PC.
* [OpenJDK 11](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot)
* [Gradle 6.5](https://gradle.org/install/) or later

Please check [full system requirements](docs/system-requirements.md) for more details. 

### Compile and Run
```
gradle clean build test
```

### Examples
* [__spring dependency injection__](spring-di) - simple dependency injection demo.
* [__spring jcasbin__](spring-jcasbin) - simple integration example of jcasbin in spring app.
* [__spring security__](spring-security) - cookie session tracking and web security.
* [__spring security-jwt__](spring-security-jwt) - JWT based web security.
* [__spring demo__](spring-demo) - basic springboot application.
* [__spring proxy__](spring-proxy) - simple springboot http proxy demo.
* [__spring fileserver__](spring-fileserver) - simple file server ove REST APIs 
* [__spring websocket__](spring-websockets) - simple websocket demo.
* [__spring_bank__](spring-bank) - simple transactional web application.

_Enjoy !_
