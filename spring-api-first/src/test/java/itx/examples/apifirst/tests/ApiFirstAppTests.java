package itx.examples.apifirst.tests;

import itx.examples.apifirst.model.CreateUser;
import itx.examples.apifirst.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiFirstAppTests {

    private static User fistUser;

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void getUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/services/users", User[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().length);
    }

    @Test
    @Order(2)
    public void createFirstUser() {
        CreateUser request = new CreateUser();
        request.setName("user-name-01");
        request.setEmail("user01@email.com");
        request.setEnabled(true);
        ResponseEntity<User> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/services/user", request, User.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        fistUser = responseEntity.getBody();
        assertNotNull(fistUser);
    }

    @Test
    @Order(3)
    public void checkUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/services/users", User[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().length);
    }

    @Test
    @Order(4)
    public void deleteFirstUser() {
        restTemplate.delete("http://localhost:" + port + "/services/users/" + fistUser.getId());
    }

    @Test
    @Order(5)
    public void checkUsersDeleted() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/services/users", User[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().length);
    }

}
