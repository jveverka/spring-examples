package itx.examples.springboot.security.springsecurity.jwt.tests;

import itx.examples.springboot.security.springsecurity.jwt.rest.dto.LoginUserNamePasswordRequest;
import itx.examples.springboot.security.springsecurity.jwt.services.dto.ServerData;
import itx.examples.springboot.security.springsecurity.jwt.services.dto.UserData;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JWTSuperAdminAccessTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String jwToken;

    @Test
    @Order(1)
    public void loginValid() throws MalformedURLException {
        LoginUserNamePasswordRequest loginUserNamePasswordRequest = new LoginUserNamePasswordRequest("zorg", "secret");
        ResponseEntity<UserData> response = restTemplate.postForEntity(
                new URL("http://localhost:" + port + "/services/security/login").toString(), loginUserNamePasswordRequest, UserData.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserData userData = response.getBody();
        assertNotNull(userData);
        jwToken = userData.getJwToken().getToken();
        assertNotNull(jwToken);
    }

    @Test
    @Order(2)
    public void getProtectedSuperAdminDataAsSuperAdmin() throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/users/super-admins/all").toString(), HttpMethod.GET, request, ServerData.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ServerData serverData = response.getBody();
        assertNotNull(serverData);
    }

    @Test
    @Order(3)
    public void loginAsDifferentUser() throws MalformedURLException {
        LoginUserNamePasswordRequest loginUserNamePasswordRequest = new LoginUserNamePasswordRequest("jane", "secret");
        ResponseEntity<UserData> response = restTemplate.postForEntity(
                new URL("http://localhost:" + port + "/services/security/login").toString(), loginUserNamePasswordRequest, UserData.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserData userData = response.getBody();
        assertNotNull(userData);
        jwToken = userData.getJwToken().getToken();
        assertNotNull(jwToken);
    }

    @Test
    @Order(4)
    public void getProtectedSuperAdminDataAsAdmin() throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        assertThrows(RestClientException.class, () -> {
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/users/super-admins/all").toString(), HttpMethod.GET, request, ServerData.class);
        });
        //assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

}
