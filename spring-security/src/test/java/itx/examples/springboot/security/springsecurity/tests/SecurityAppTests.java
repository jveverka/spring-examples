package itx.examples.springboot.security.springsecurity.tests;

import itx.examples.springboot.security.springsecurity.services.dto.LoginRequest;
import itx.examples.springboot.security.springsecurity.services.dto.ServerData;
import itx.examples.springboot.security.springsecurity.services.dto.UserData;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityAppTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String cookie;

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
    public void testValidLogin() throws MalformedURLException {
        LoginRequest loginRequest = new LoginRequest("jane", "secret");
        ResponseEntity<UserData> response = restTemplate.postForEntity(
                new URL("http://localhost:" + port + "/services/security/login").toString(), loginRequest, UserData.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserData userData = response.getBody();
        assertNotNull(userData);
        List<String> cookies = response.getHeaders().get("Set-Cookie");
        Optional<String> jsessionId = cookies.stream().filter(c -> c.startsWith("JSESSIONID=")).findFirst();
        assertTrue(jsessionId.isPresent());
        cookie = jsessionId.get();
    }

    @Test
    @Order(3)
    public void testGetDataForUsers() throws MalformedURLException {
        HttpEntity<Void> request = new HttpEntity<>(createHttpHeaders());
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/users/all").toString(), HttpMethod.GET, request, ServerData.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ServerData serverData = response.getBody();
        assertNotNull(serverData);
    }

    @Test
    @Order(4)
    public void testGetDataForAdmins() throws MalformedURLException {
        HttpEntity<Void> request = new HttpEntity<>(createHttpHeaders());
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/admins/all").toString(),  HttpMethod.GET, request, ServerData.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ServerData serverData = response.getBody();
        assertNotNull(serverData);
    }

    @Test
    @Order(5)
    public void logoutValid() throws MalformedURLException {
        HttpEntity<Void> request = new HttpEntity<>(createHttpHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/security/logout").toString(), HttpMethod.GET, request, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testGetDataForUsersLogout() throws MalformedURLException {
        HttpEntity<Void> request = new HttpEntity<>(createHttpHeaders());
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/users/all").toString(), HttpMethod.GET, request, ServerData.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void testGetDataForAdminsLogout() throws MalformedURLException {
        HttpEntity<Void> request = new HttpEntity<>(createHttpHeaders());
        ResponseEntity<ServerData> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/services/data/admins/all").toString(), HttpMethod.GET, request, ServerData.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        return headers;
    }

}
