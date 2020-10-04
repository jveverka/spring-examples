package itx.examples.springboot.demo;

import itx.examples.springboot.demo.dto.SystemInfo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateContainerTests {

    private static final Logger LOG = LoggerFactory.getLogger(CreateContainerTests.class);

    private static GenericContainer<?> springDemoContainer;
    private static TestRestTemplate restTemplate;

    @BeforeAll
    public static void init() {
        String jarFilePath = "build/libs/spring-demo-1.0.0-SNAPSHOT.jar";
        assertTrue(Files.exists(Path.of(jarFilePath)));
        MountableFile mountableFile = MountableFile.forHostPath(jarFilePath);
        springDemoContainer = new GenericContainer<>("adoptopenjdk/openjdk11:jre-11.0.8_10-alpine")
                .withExposedPorts(8081)
                .withCopyFileToContainer(mountableFile, "/spring-demo-1.0.0-SNAPSHOT.jar")
                .withCommand("java", "-Xms32m", "-Xms128M", "-jar", "/spring-demo-1.0.0-SNAPSHOT.jar");
        Startables.deepStart(Stream.of(springDemoContainer)).join();
        restTemplate = new TestRestTemplate();
    }

    @Test
    public void testCreatedContainer() {
        Integer port = springDemoContainer.getMappedPort(8081);
        LOG.info("spring-demo port: {}", port);
        ResponseEntity<SystemInfo> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/data/info", SystemInfo.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @AfterAll
    public static void shutdown() {
        springDemoContainer.stop();
    }

}
