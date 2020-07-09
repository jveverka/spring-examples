package itx.examples.springbank.client.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.springbank.client.service.AdminServiceImpl;
import itx.examples.springbank.client.service.BankServiceImpl;
import itx.examples.springbank.client.service.SystemInfoServiceImpl;
import itx.examples.springbank.common.dto.Client;
import itx.examples.springbank.common.dto.ClientId;
import itx.examples.springbank.common.dto.CreateClientRequest;
import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.SystemInfo;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankClientTestsIT {

    private static AdminService adminService;
    private static BankService bankService;
    private static SystemInfoService systemInfoService;

    private static ClientId clientId01;
    private static ClientId clientId02;

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
    }

    @Test
    @Order(5)
    public void removeClient01() throws ServiceException {
        adminService.deleteClient(clientId01);
        Collection<Client> clients = adminService.getClients();
        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    @Test
    @Order(6)
    public void removeClient02() throws ServiceException {
        adminService.deleteClient(clientId02);
        Collection<Client> clients = adminService.getClients();
        assertNotNull(clients);
        assertEquals(0, clients.size());
    }

}