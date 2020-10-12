package itx.examples.springdata.test;

import itx.examples.springdata.dto.CreateUserRequest;
import itx.examples.springdata.dto.UserData;
import itx.examples.springdata.dto.UserId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = DataAppTests.Initializer.class)
public class DataAppTests {

    private static UserId fistUserId;

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void getUsers() {
        ResponseEntity<UserData[]> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/services/users", UserData[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().length);
    }

    @Test
    @Order(2)
    public void createFirstUser() {
        CreateUserRequest request = new CreateUserRequest("user-name-01", "user01@email.com");
        ResponseEntity<UserId> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/services/users", request, UserId.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        fistUserId = responseEntity.getBody();
        assertNotNull(fistUserId);
    }

    @Test
    @Order(3)
    public void checkUsers() {
        ResponseEntity<UserData[]> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/services/users", UserData[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().length);
    }

    @Test
    @Order(4)
    public void deleteFirstUser() {
        restTemplate.delete("http://localhost:" + port + "/services/users/" + fistUserId.getId());
    }

    @Test
    @Order(5)
    public void checkUsersDeleted() {
        ResponseEntity<UserData[]> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/services/users", UserData[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().length);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12.3");

        private static void startContainers() {
            Startables.deepStart(Stream.of(postgres)).join();
        }

        private static Map<String, String> createContextConfiguration() {
            return Map.of(
                    "spring.datasource.url", postgres.getJdbcUrl(),
                    "spring.datasource.username", postgres.getDatabaseName(),
                    "spring.datasource.password", postgres.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testContainers = new MapPropertySource("testcontainers", (Map) createContextConfiguration());
            environment.getPropertySources().addFirst(testContainers);
        }

    }

}
