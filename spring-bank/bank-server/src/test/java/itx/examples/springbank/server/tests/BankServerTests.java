package itx.examples.springbank.server.tests;

import itx.examples.springbank.common.dto.AccountId;
import itx.examples.springbank.common.dto.Client;
import itx.examples.springbank.common.dto.ClientId;
import itx.examples.springbank.common.dto.CreateClientRequest;
import itx.examples.springbank.common.dto.DepositRequest;
import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.SystemInfo;
import itx.examples.springbank.common.dto.TransactionRequest;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("tests")
public class BankServerTests {

    private static ClientId clientId01;
    private static AccountId accountId01;
    private static ClientId clientId02;
    private static AccountId accountId02;

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
        CreateClientRequest request = new CreateClientRequest("John", "Doe");
        ResponseEntity<ClientId> response = restTemplate.postForEntity("http://localhost:" + port + "/services/admin/client", request, ClientId.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        clientId01 = response.getBody();
        assertNotNull(clientId01);
    }

    @Test
    @Order(3)
    public void testCreateClient02() throws ServiceException {
        CreateClientRequest request = new CreateClientRequest("Jane", "Doe");
        ResponseEntity<ClientId> response = restTemplate.postForEntity("http://localhost:" + port + "/services/admin/client", request, ClientId.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        clientId02 = response.getBody();
        assertNotNull(clientId02);
    }

    @Test
    @Order(4)
    public void testCheckClients() throws ServiceException {
        Client[] clients = getAllClients();
        assertEquals(2, clients.length);
        Collection<Client> clientCollection = Arrays.asList(clients);
        Optional<Client> client01 = clientCollection.stream().filter(c->c.getId().equals(clientId01)).findFirst();
        assertTrue(client01.isPresent());
        accountId01 = client01.get().getAccount().getId();
        assertEquals(0L, client01.get().getAccount().getCredit());
        Optional<Client> client02 = clientCollection.stream().filter(c->c.getId().equals(clientId02)).findFirst();
        assertTrue(client02.isPresent());
        accountId02 = client02.get().getAccount().getId();
        assertEquals(0L, client02.get().getAccount().getCredit());
    }

    @Test
    @Order(5)
    public void testDepositCreditToClients() throws ServiceException {
        DepositRequest request01 = new DepositRequest(accountId01, 100L);
        restTemplate.put("http://localhost:" + port + "/services/bank/deposit", request01);
        DepositRequest request02 = new DepositRequest(accountId02, 100L);
        restTemplate.put("http://localhost:" + port + "/services/bank/deposit", request02);
    }

    @Test
    @Order(6)
    public void testCheckClientBalances() throws ServiceException {
        Client[] clients = getAllClients();
        assertEquals(100L, clients[0].getAccount().getCredit());
        assertEquals(100L, clients[1].getAccount().getCredit());
    }

    @Test
    @Order(7)
    public void testTransferFunds() throws ServiceException {
        TransactionRequest request = new TransactionRequest(accountId01, accountId02, 100L);
        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/services/bank/transfer", request, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(8)
    public void testCheckClientBalancesAgain() throws ServiceException {
        Client[] clients = getAllClients();
        assertEquals(0L, clients[0].getAccount().getCredit());
        assertEquals(200L, clients[1].getAccount().getCredit());
    }

    @Test
    @Order(9)
    public void removeClient01() throws ServiceException {
        restTemplate.delete("http://localhost:" + port + "/services/admin/client/" + clientId01.getId());
        Client[] clients = getAllClients();
        assertEquals(1, clients.length);
    }

    @Test
    @Order(10)
    public void removeClient02() throws ServiceException {
        restTemplate.delete("http://localhost:" + port + "/services/admin/client/" + clientId02.getId());
        Client[] clients = getAllClients();
        assertEquals(0, clients.length);
    }

    private Client[] getAllClients() {
        ResponseEntity<Client[]> response = restTemplate.getForEntity("http://localhost:" + port + "/services/admin/clients", Client[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        return response.getBody();
    }

}
