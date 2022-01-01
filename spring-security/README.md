# Simple SpringBoot security demo

Really simple spring security demo. Reference tokens (cookie JSESSIONID) are used for client session tracking. 
This demo application has following REST end-points:

* __security__ - login / logout - provides JSESSIONID cookie, force invalidate client's session.
* __protected data__ - accessible only after login for clients with valid JSESSIONID cookie.
* __public data__ - accessible to any client, no login (no JSESSIONID cookie) required.

Public data is accessible without login.
Protected data is accessible only after login. Session timeout is set to 5 minutes.
After login, each request must use same cookie JSESSIONID, because server is tracking http sessions by this cookie.

* __Authentication__ is handled by internal service ``itx.examples.springboot.security.springsecurity.services.UserAccessService``
* __Authorization__ is handled by Spring's Method Security, RBAC model is used.

### Users, Passwords and Roles
* joe / secret, ROLE_USER
* jane / secret, ROLE_USER, ROLE_ADMIN
* alice / secret, ROLE_PUBLIC

### Security - Login
Client presents itself with username / password credentials. After credentials match, server 
produces JSESSIONID for session tracking.
* __POST__ http://localhost:8888/services/security/login
  ```
  {
    "userName": "joe",
    "password": "secret"
  }
  ```
### Security - Logout
This action revokes client's http session and related JSESSIONID cookie.
* __GET__ http://localhost:8888/services/security/logout

### Get protected data
GET protected data for different user roles:
* __GET__ http://localhost:8888/services/data/users/all (ROLE_USER, ROLE_ADMIN)
* __GET__ http://localhost:8888/services/data/admins/all (ROLE_ADMIN)

### Get public data
* __GET__ http://localhost:8888/services/public/data/all (all roles, ROLE_PUBLIC, or no login required)

### Build and run
```
gradle clean build test
java -jar build/libs/spring-security-1.0.0-SNAPSHOT.jar 
```