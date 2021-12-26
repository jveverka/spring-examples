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

### Build Classic Fat jar
* Build springboot fat jar.
```
gradle clean build test jar
```
* Build docker with springboot fat jar.
```
export VERSION=0.0.1-SNAPSHOT
export ARCH=arm64v8
export ARCH=amd64
docker build -t jurajveverka/spring-native-classic:${VERSION}-${ARCH} \
  --build-arg ARCH=${ARCH} \
  -f Dockerfile.classic .
  
docker push jurajveverka/spring-native-classic:${VERSION}-${ARCH}
docker run -p 8080:8080 jurajveverka/spring-native-classic:${VERSION}-${ARCH}
``` 

### Lightweight Container with Cloud Native Buildpacks
```
gradle clean build test bootBuildImage
docker run --rm -p 8080:8080 spring-native:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools
* Build native binary.
```
sdk use java 21.3.0.r17-grl
sdk use gradle 7.3.3
gradle clean build test nativeCompile
./build/native/nativeCompile/spring-native
```
* Build docker with native binary.
```
export VERSION=0.0.1-SNAPSHOT
export ARCH=arm64v8
export ARCH=amd64
docker build -t jurajveverka/spring-native-native:${VERSION}-${ARCH} \
  --build-arg ARCH=${ARCH} \
  -f Dockerfile.native .
  
docker push jurajveverka/spring-native-native:${VERSION}-${ARCH}
docker run -p 8080:8080 jurajveverka/spring-native-native:${VERSION}-${ARCH}
```

#### References
* [Spring Native](https://docs.spring.io/spring-native/docs/0.11.1/reference/htmlsingle/#overview)
* [GraalVM Native Image](https://www.graalvm.org/reference-manual/native-image/)
