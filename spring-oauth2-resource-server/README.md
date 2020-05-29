# OAuth2 Resource-Server example

*WIP*

1. Create development account on [auth0.com](https://auth0.com/)
2. Create application __Default App__ 
   *  create user john@example.com [rith9uSail5a]
3. Use postman to get OAuth 2.0 JWT __access_token__
   * Grant Type: Authorization Code
   * Callback URL: https://www.getpostman.com/oauth2/callback
   * Auth URL: https://dev-yrazowjp.eu.auth0.com/authorize
   * Access Token URL: https://dev-yrazowjp.eu.auth0.com/oauth/token
   * Client ID: [Client ID of Default App]
   * Client Secret: [Client Secret of Default App]
4. Set proper URIs on [application.yml](src/main/resources/application.yml)
5. Start this demo on ``http://localhost:8081``
6. Use __access_token__ to get data from local service
   *  __GET__ ``http://localhost:8081/services/data`` 
   * valid access_token: 200 OK
   * invalid access_token: 401 Unauthorized
