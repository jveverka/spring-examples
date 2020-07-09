# Bank demo


## Build and Run
```
gradle clean install test
```

## Run PostgreSQL locally in Docker
```
docker run -p 5432:5432 --name postgres-server -e POSTGRES_PASSWORD=secret -d postgres:12.3-alpine
docker stop postgres-server
docker rm postgres-server
```

### Full stack Integration Tests
Use docker-compose to run complete stack:
1. postgresql on localhost:5432
2. bank-server on localhost:8080
```
#1. Start PostgreSQL Database and Bank-Server as docker containers 
docker-compose up --build -d
#2. Runt integration test suite
gradle :bank-client:clean :bank-client:test -Dtest.profile=integration
#3. tear down docker containers
docker-compose down -v --rmi all --remove-orphans
```