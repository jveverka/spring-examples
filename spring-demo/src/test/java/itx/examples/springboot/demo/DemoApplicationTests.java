package itx.examples.springboot.demo;


import itx.examples.springboot.demo.dto.DataMessage;
import itx.examples.springboot.demo.dto.RequestInfo;
import itx.examples.springboot.demo.dto.SystemInfo;
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

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@Order(1)
	public void testSystemInfo() {
		ResponseEntity<SystemInfo> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/data/info", SystemInfo.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		SystemInfo systemInfo = response.getBody();
		assertNotNull(systemInfo);
		assertEquals("spring-demo", systemInfo.getName());
		assertEquals("1.0.0", systemInfo.getVersion());
		assertTrue(systemInfo.getTimestamp() > 0);
	}

	@Test
	@Order(2)
	public void testBuildInfo() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/data/build-info", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(3)
	public void testGetMessage() {
		DataMessage message = new DataMessage("hi");
		ResponseEntity<DataMessage> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/data/message", message, DataMessage.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		DataMessage dataMessage = response.getBody();
		assertNotNull(dataMessage);
		assertEquals(message.getData(), dataMessage.getData());
	}

	@Test
	@Order(4)
	public void testGetEcho() {
		String message = "hi";
		ResponseEntity<Void> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/data/echo/" + message, Void.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(5)
	public void testRequestTester() {
		ResponseEntity<RequestInfo> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/data/test", RequestInfo.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(6)
	public void testApiDocJson() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/v3/api-docs", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(7)
	public void testApiDocYaml() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/v3/api-docs.yaml", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}