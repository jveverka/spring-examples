package itx.examples.springbank.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.SystemInfo;
import itx.examples.springbank.common.service.SystemInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SystemInfoServiceImpl implements SystemInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInfoServiceImpl.class);

    private final static String SERVICE_PREFIX = "/services/system";

    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private String baseUri;

    public SystemInfoServiceImpl(HttpClient httpClient, String baseUri, ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.baseUri = baseUri + SERVICE_PREFIX;
        this.mapper = mapper;
    }

    @Override
    public SystemInfo getSystemInfo() throws ServiceException  {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(baseUri + "/version"))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ServiceException();
            }
            return mapper.readValue(response.body(), SystemInfo.class);
        } catch (Exception e) {
            LOG.error("ERROR: ", e);
            throw new ServiceException();
        }
    }

}
