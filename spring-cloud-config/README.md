# Spring Cloud Config

### 1. Create Git config repo
```
./create-git-config-repo.sh
```

### 2. 

### 3. Git repo structure
```
cloud-config-client.yml
nginx-conf-default/nginx.conf
```

* GET http://localhost:8888/cloud-config-client.yml
* GET http://localhost:8888/cloud-config-client/default
* GET http://localhost:8888/application/default/main/nginx-conf-default/nginx.conf


#### References
* [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/reference/html/#_quick_start)
* [Spring Cloud Config Client](https://cloud.spring.io/spring-cloud-config/reference/html/#_spring_cloud_config_client)