package itx.examples.springbank.client.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.springbank.client.service.AdminServiceImpl;
import itx.examples.springbank.client.service.BankServiceImpl;
import itx.examples.springbank.client.service.SystemInfoServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankClientTestsIT {

    private static AdminService adminService;
    private static BankService bankService;
    private static SystemInfoService systemInfoService;

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
    }

}
