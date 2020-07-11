package itx.examples.springboot.di.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DITests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testPrintDefault() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/service/print-default", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String message = response.getBody();
        assertNotNull(message);
    }

    @Test
    public void testPrintErr() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/service/print-err", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String message = response.getBody();
        assertNotNull(message);
    }

    @Test
    public void testPrintOut() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/service/print-out", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String message = response.getBody();
        assertNotNull(message);
    }

    @Test
    public void testGetData() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/service/data", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String message = response.getBody();
        assertNotNull(message);
    }

    @Test
    public void testGetDataPrototype() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/service/data-prototype", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String message = response.getBody();
        assertNotNull(message);
    }

}
