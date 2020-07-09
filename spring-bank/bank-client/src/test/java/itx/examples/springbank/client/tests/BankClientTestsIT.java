package itx.examples.springbank.client.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.springbank.client.service.AdminServiceImpl;
import itx.examples.springbank.client.service.BankServiceImpl;
import itx.examples.springbank.client.service.SystemInfoServiceImpl;
import itx.examples.springbank.common.dto.AccountId;
import itx.examples.springbank.common.dto.Client;
import itx.examples.springbank.common.dto.ClientId;
import itx.examples.springbank.common.dto.CreateClientRequest;
import itx.examples.springbank.common.dto.DepositRequest;
import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.SystemInfo;
import itx.examples.springbank.common.dto.TransactionRequest;
import itx.examples.springbank.common.dto.TransferRequest;
import itx.examples.springbank.common.service.AdminService;
import itx.examples.springbank.common.service.BankService;
import itx.examples.springbank.common.service.SystemInfoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.net.http.HttpClient;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankClientTestsIT {

    private static AdminService adminService;
    private static BankService bankService;
    private static SystemInfoService systemInfoService;

    private static ClientId clientId01;
    private static AccountId accountId01;
    private static ClientId clientId02;
    private static AccountId accountId02;

    @BeforeAll
    private static void init() {
        String baseUri = "http://localhost:8080";
        ObjectMapper mapper = new ObjectMapper();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        adminService = new AdminServiceImpl(httpClient, baseUri, mapper);
        bankService = new BankServiceImpl(httpClient, baseUri, mapper);
        systemInfoService = new SystemInfoServiceImpl(httpClient, baseUri, mapper);
    }

    @Test
    @Order(1)
    public void testSystemInfo() throws ServiceException {
        SystemInfo systemInfo = systemInfoService.getSystemInfo();
        assertNotNull(systemInfo);
        assertNotNull(systemInfo.getVersion());
        Collection<Client> clients = adminService.getClients();
        assertNotNull(clients);
        assertEquals(0, clients.size());
    }

    @Test
    @Order(2)
    public void testCreateClient01() throws ServiceException {
        CreateClientRequest request = new CreateClientRequest("John", "Doe");
        clientId01 = adminService.createClient(request);
        assertNotNull(clientId01);
        assertNotNull(clientId01.getId());
    }

    @Test
    @Order(3)
    public void testCreateClient02() throws ServiceException {
        CreateClientRequest request = new CreateClientRequest("Jane", "Doe");
        clientId02 = adminService.createClient(request);
        assertNotNull(clientId02);
        assertNotNull(clientId02.getId());
    }

    @Test
    @Order(4)
    public void testCheckClients() throws ServiceException {
        Collection<Client> clients = adminService.getClients();
        assertNotNull(clients);
        assertEquals(2, clients.size());
        Optional<Client> client01 = clients.stream().filter(c->c.getId().equals(clientId01)).findFirst();
        assertTrue(client01.isPresent());
        accountId01 = client01.get().getAccount().getId();
        assertEquals(0L, client01.get().getAccount().getCredit());
        Optional<Client> client02 = clients.stream().filter(c->c.getId().equals(clientId02)).findFirst();
        assertTrue(client02.isPresent());
        accountId02 = client02.get().getAccount().getId();
        assertEquals(0L, client02.get().getAccount().getCredit());
    }

    @Test
    @Order(5)
    public void testDepositCreditToClients() throws ServiceException {
        DepositRequest request01 = new DepositRequest(accountId01, 100L);
        bankService.deposit(request01);
        DepositRequest request02 = new DepositRequest(accountId02, 100L);
        bankService.deposit(request02);
    }

    @Test
    @Order(6)
    public void testCheckClientBalances() throws ServiceException {
        Collection<Client> clients = adminService.getClients();
        assertNotNull(clients);
        assertEquals(2, clients.size());
        Optional<Client> client01 = clients.stream().filter(c->c.getId().equals(clientId01)).findFirst();
        assertTrue(client01.isPresent());
        assertEquals(100L, client01.get().getAccount().getCredit());
        Optional<Client> client02 = clients.stream().filter(c->c.getId().equals(clientId02)).findFirst();
        assertTrue(client02.isPresent());
        assertEquals(100L, client02.get().getAccount().getCredit());
    }

    @Test
    @Order(7)
    public void testTransferFunds() throws ServiceException {
        TransactionRequest request = new TransactionRequest(accountId01, accountId02, 100L);
        bankService.transferFunds(request);
    }

    @Test
    @Order(8)
    public void testCheckClientBalancesAgain() throws ServiceException {
        Collection<Client> clients = adminService.getClients();
        assertNotNull(clients);
        assertEquals(2, clients.size());
        Optional<Client> client01 = clients.stream().filter(c->c.getId().equals(clientId01)).findFirst();
        assertTrue(client01.isPresent());
        assertEquals(0L, client01.get().getAccount().getCredit());
        Optional<Client> client02 = clients.stream().filter(c->c.getId().equals(clientId02)).findFirst();
        assertTrue(client02.isPresent());
        assertEquals(200L, client02.get().getAccount().getCredit());
    }

    @Test
    @Order(9)
    public void removeClient01() throws ServiceException {
        adminService.deleteClient(clientId01);
        Collection<Client> clients = adminService.getClients();
        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    @Test
    @Order(10)
    public void removeClient02() throws ServiceException {
        adminService.deleteClient(clientId02);
        Collection<Client> clients = adminService.getClients();
        assertNotNull(clients);
        assertEquals(0, clients.size());
    }

}
