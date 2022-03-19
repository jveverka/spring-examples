package itx.examples.springboot.redis.tests;

import itx.examples.springboot.redis.model.MessageData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(initializers = RedisAppTests.Initializer.class)
class RedisAppTests {

    protected static final int DOCKER_EXPOSED_REDIS_PORT = 6379;
    private static final String REDIS_DOCKER_IMAGE = "redis:6";
    protected static GenericContainer<?> redisContainer;
    private static String messageId;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void testEmptyRepo() {
        ResponseEntity<MessageData[]> responseEntity = restTemplate.getForEntity("/services/data/messages", MessageData[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().length);
    }

    @Test
    @Order(2)
    void createMessages() {
        ResponseEntity<MessageData> responseEntity = restTemplate.postForEntity("/services/data/messages/" + "hi-message", null, MessageData.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals("hi-message", responseEntity.getBody().getMessage());
        messageId = responseEntity.getBody().getId();

        ResponseEntity<MessageData[]> responsesEntity = restTemplate.getForEntity("/services/data/messages", MessageData[].class);
        assertEquals(HttpStatus.OK, responsesEntity.getStatusCode());
        assertEquals(1, responsesEntity.getBody().length);
    }

    @Test
    @Order(3)
    void getMessage() {
        ResponseEntity<MessageData> responseEntity = restTemplate.getForEntity("/services/data/messages/" + messageId, MessageData.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals("hi-message", responseEntity.getBody().getMessage());
    }

    @Test
    @Order(4)
    void deleteMessage() {
        restTemplate.delete("/services/data/messages/" + messageId);
        ResponseEntity<MessageData[]> responsesEntity = restTemplate.getForEntity("/services/data/messages", MessageData[].class);
        assertEquals(HttpStatus.OK, responsesEntity.getStatusCode());
        assertEquals(0, responsesEntity.getBody().length);
    }

    @Test
    @Order(5)
    void testOptions() {
        Set<HttpMethod> httpMethods = restTemplate.optionsForAllow("/services/data/messages");
        assertEquals(3, httpMethods.size());
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        private final Logger LOGGER = LoggerFactory.getLogger(RedisAppTests.class);
        static GenericContainer<?> redisContainer = new GenericContainer<>(REDIS_DOCKER_IMAGE)
                .withExposedPorts(DOCKER_EXPOSED_REDIS_PORT)
                .withReuse(true);
        @Override
        public void initialize(@NonNull ConfigurableApplicationContext context) {
            redisContainer.start();
            Assertions.assertTrue(redisContainer.isRunning());
            RedisAppTests.redisContainer = redisContainer;
            LOGGER.info("REDIS     : {}:{}", redisContainer.getContainerIpAddress(), redisContainer.getMappedPort(DOCKER_EXPOSED_REDIS_PORT));
            TestPropertyValues.of(
                    "spring.redis.host=" + redisContainer.getContainerIpAddress(),
                    "spring.redis.port=" + redisContainer.getMappedPort(DOCKER_EXPOSED_REDIS_PORT)
            ).applyTo(context.getEnvironment());
        }
    }

}
