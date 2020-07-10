package itx.examples.springbank.server.tests;

import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.SystemInfo;
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
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("tests")
public class BankServerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testSystemInfo() {
        ResponseEntity<SystemInfo> response = restTemplate.getForEntity("http://localhost:" + port + "/services/system/version", SystemInfo.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SystemInfo systemInfo = response.getBody();
        assertNotNull(systemInfo);
        assertNotNull(systemInfo.getVersion());
    }

    @Test
    @Order(2)
    public void testCreateClient01() throws ServiceException {
        //TODO
    }

    @Test
    @Order(3)
    public void testCreateClient02() throws ServiceException {
        //TODO
    }

    @Test
    @Order(4)
    public void testCheckClients() throws ServiceException {
        //TODO
    }

    @Test
    @Order(5)
    public void testDepositCreditToClients() throws ServiceException {
        //TODO
    }

    @Test
    @Order(6)
    public void testCheckClientBalances() throws ServiceException {
        //TODO
    }

    @Test
    @Order(7)
    public void testTransferFunds() throws ServiceException {
        //TODO
    }

    @Test
    @Order(8)
    public void testCheckClientBalancesAgain() throws ServiceException {
        //TODO
    }

    @Test
    @Order(9)
    public void removeClient01() throws ServiceException {
        //TODO
    }

    @Test
    @Order(10)
    public void removeClient02() throws ServiceException {
        //TODO
    }

}
