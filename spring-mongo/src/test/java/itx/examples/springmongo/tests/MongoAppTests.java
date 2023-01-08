package itx.examples.springmongo.tests;

import itx.examples.springmongo.dto.CreateRequest;
import itx.examples.springmongo.dto.Message;
import itx.examples.springmongo.dto.MessageId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = MongoAppTests.Initializer.class)
public class MongoAppTests {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void getMessages() {
        ResponseEntity<Message[]> responseEntity = restTemplate.getForEntity("/services/messages", Message[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().length);
    }

    @Test
    @Order(2)
    void getSaveMessages() {
        CreateRequest request = new CreateRequest("hi");
        ResponseEntity<MessageId> responseCreate = restTemplate.postForEntity("/services/messages", request, MessageId.class);
        assertNotNull(responseCreate.getBody());
        assertNotNull(responseCreate.getBody().id());

        ResponseEntity<Message[]> responseEntity1 = restTemplate.getForEntity("/services/messages", Message[].class);
        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
        assertEquals(1, responseEntity1.getBody().length);
        assertEquals(responseEntity1.getBody()[0].id(), responseCreate.getBody().id());
        assertEquals(responseEntity1.getBody()[0].message(), "hi");

        restTemplate.delete("/services/messages/" + responseCreate.getBody().id());

        ResponseEntity<Message[]> responseEntity2 = restTemplate.getForEntity("/services/messages", Message[].class);
        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
        assertEquals(0, responseEntity2.getBody().length);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static int DOCKER_EXPOSED_MONGO_PORT = 27017;
        static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5.0.5")).withExposedPorts(DOCKER_EXPOSED_MONGO_PORT);

        private static void startContainers() {
            Startables.deepStart(Stream.of(mongoDBContainer)).join();
        }
        /*private static Map<String, String> createContextConfiguration() {
            return Map.of(
                    "spring.data.mongodb.host=" + mongoIp,
                    "spring.data.mongodb.port=" + mongoBoundPort
            );
        }*/

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            startContainers();

            Integer mongoBoundPort = mongoDBContainer.getMappedPort(DOCKER_EXPOSED_MONGO_PORT);
            String mongoIp = mongoDBContainer.getContainerIpAddress();

            ConfigurableEnvironment environment = context.getEnvironment();
            //MapPropertySource testContainers = new MapPropertySource("testcontainers", (Map) createContextConfiguration());
            //environment.getPropertySources().addFirst(testContainers);

            TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongoIp,
                    "spring.data.mongodb.port=" + mongoBoundPort,
                    "spring.data.mongodb.database=test"
            ).applyTo(context.getEnvironment());
        }

    }

}
