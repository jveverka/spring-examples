# Spring Native Demo

### Build
```
gradle clean build test
```

### Lightweight Container with Cloud Native Buildpacks
```
gradle bootBuildImage
docker run --rm -p 8080:8080 springnative:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools
```
gradle nativeBuild
build/native-image/springnative
```

#### References
* [Spring Native](https://docs.spring.io/spring-native/docs/0.11.1/reference/htmlsingle/#overview)