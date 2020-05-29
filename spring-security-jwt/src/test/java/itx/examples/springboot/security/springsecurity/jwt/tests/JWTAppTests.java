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

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JWTAppTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String jwToken;

    @Test
    @Order(1)
    public void getPublicData() throws MalformedURLException {
        ResponseEntity<ServerData> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/services/public/data/all").toString(), ServerData.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ServerData serverData = response.getBody();
        assertNotNull(serverData);
        assertNotNull(serverData.getData());
        assertEquals("Public", serverData.getSource());
        assertTrue(serverData.getTimestamp() > 0);
    }

    @Test
    @Order(2)
    public void loginValid() throws MalformedURLException {
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
    @Order(3)
    public void getProtectedDataUsers() throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/users/all").toString(), HttpMethod.GET, request, ServerData.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ServerData serverData = response.getBody();
        assertNotNull(serverData);
    }

    @Test
    @Order(4)
    public void getProtectedDataAdmins() throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/admins/all").toString(), HttpMethod.GET, request, ServerData.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ServerData serverData = response.getBody();
        assertNotNull(serverData);
    }

    @Test
    @Order(5)
    public void logoutValid() throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/security/logout").toString(), HttpMethod.GET, request, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void getProtectedDataUsersInvalidAccessToken() throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/users/all").toString(), HttpMethod.GET, request, ServerData.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void getProtectedDataAdminsInvalidAccessToken() throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/admins/all").toString(), HttpMethod.GET, request, ServerData.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(8)
    public void logoutInvalidAccessToken() throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/security/logout").toString(), HttpMethod.GET, request, Void.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(9)
    public void loginInvalid() throws MalformedURLException {
        LoginUserNamePasswordRequest loginUserNamePasswordRequest = new LoginUserNamePasswordRequest("jane", "zzzzz");
        ResponseEntity<UserData> response = restTemplate.postForEntity(
                new URL("http://localhost:" + port + "/services/security/login").toString(), loginUserNamePasswordRequest, UserData.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

}
