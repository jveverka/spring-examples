package itx.spring.demo.proxy.tests;

import itx.spring.demo.proxy.controller.ProxyController;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProxyControllerTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyControllerTests.class);

    @LocalServerPort
    int port;

    @Autowired
    ProxyController proxyController;

    @Autowired
    TestRestTemplate restTemplate;

    private static MockWebServer server;

    @BeforeAll
    public static void init() throws IOException {
        server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("hello from mocked webserver !"));
        server.start();
    }

    @Test
    void testProxyGet() {
        LOGGER.info("ProxyControllerTests:: server: {} mockServer: {}", restTemplate.getRootUri(), server.url(""));
        String baseUrl = server.url("/data").toString();
        proxyController.setBaseUrl(baseUrl);

        ResponseEntity<String> response = restTemplate.getForEntity("/services/data", String.class);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("hello from mocked webserver !", response.getBody());
    }

    @AfterAll
    public static void destroy() throws IOException {
        if (server != null) {
            server.shutdown();
        }
    }

}
