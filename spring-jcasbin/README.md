# Simple SpringBoot security demo with jCasbin

Really simple spring security demo. Protected data is accessible only after login. 
After login, each request must use same cookie JSESSIONID, because server is tracking http sessions by this cookie.

* __Authentication__ is handled by internal service ``itx.examples.springboot.security.services.UserAccessService``
* __Authorization__ is handled by [jCasbin](https://github.com/casbin/jcasbin) library using RBAC model example in 
combination with Spring's Method Security.

### Login
* __POST__ http://localhost:8888/services/security/login
```
{
	"userName": "bob",
	"password": "secret"
}
```
### Logout
* __GET__ http://localhost:8888/services/security/logout

### Users, Passwords and Roles
* alice / secret, ROLE_USER
* bob / secret, ROLE_USER, ROLE_ADMIN

### Get protected data
GET protected data for different user roles:
* __GET__ http://localhost:8888/services/data/users/all (ROLE_USER, ROLE_ADMIN)
* __GET__ http://localhost:8888/services/data/admins/all (ROLE_ADMIN)

### Change protected data
Set protected data for admin user roles:
* __PUT__ http://localhost:8888/services/data/admins/state/{value} (ROLE_ADMIN)

### Build and run
```
gradle clean build 
java -jar build/libs/spring-jcasbin-0.0.1-SNAPSHOT.jar 
```