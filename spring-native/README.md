# Spring Native Demo

## Install Graal VM and tools
```
sudo apt install zip unzip curl
sudo apt install build-essential libz-dev zlib1g-dev
curl -s "https://get.sdkman.io" | bash
sdk install java 21.3.0.r17-grl
sdk install gradle 7.3.3
gu install native-image
```

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
sdk use java 21.3.0.r17-grl
sdk use gradle 7.3.3
gradle nativeBuild
./build/native/nativeCompile/springnative
```

#### References
* [Spring Native](https://docs.spring.io/spring-native/docs/0.11.1/reference/htmlsingle/#overview)
* [GraalVM Native Image](https://www.graalvm.org/reference-manual/native-image/)
