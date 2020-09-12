package itx.examples.springboot.demo.controller;

import itx.examples.springboot.demo.config.ApplicationConfig;
import itx.examples.springboot.demo.dto.DataMessage;
import itx.examples.springboot.demo.dto.RequestInfo;
import itx.examples.springboot.demo.dto.generic.ComplexDataPayload;
import itx.examples.springboot.demo.dto.generic.DataMarker;
import itx.examples.springboot.demo.dto.generic.GenericRequest;
import itx.examples.springboot.demo.dto.generic.GenericResponse;
import itx.examples.springboot.demo.dto.SystemInfo;
import itx.examples.springboot.demo.dto.generic.SimpleDataPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/data")
public class DataServiceController {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceController.class);

    private final ApplicationConfig applicationConfig;
    private final BuildProperties buildProperties;

    public DataServiceController(@Autowired ApplicationConfig applicationConfig,
                                 @Autowired(required = false) BuildProperties buildProperties) {
        this.applicationConfig = applicationConfig;
        this.buildProperties = buildProperties;
    }

    @GetMapping(path = "/build-info", produces = MediaType.APPLICATION_JSON_VALUE )
    public BuildProperties getBuildInfo() {
        LOG.info("getBuildInfo:");
        if (buildProperties != null) {
            return buildProperties;
        } else {
            Properties entries = new Properties();
            return new BuildProperties(entries);
        }
    }

    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE )
    public SystemInfo getSystemInfo() {
        LOG.info("getSystemInfo:");
        return new SystemInfo(applicationConfig.getId(),  "spring-demo", "1.0.0", System.currentTimeMillis());
    }

    @PostMapping(path = "/message", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<DataMessage> getDataMessage(@RequestBody DataMessage dataMessage) {
        LOG.info("getDataMessage: {}", dataMessage.getData());
        DataMessage responseMessage = new DataMessage(dataMessage.getData());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(responseMessage, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(path = "/echo/{message}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<DataMessage> getEcho(@PathVariable String message) {
        LOG.info("getEcho: {}", message);
        DataMessage responseMessage = new DataMessage(message);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PostMapping(path = "/generics", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<? extends DataMarker> getGenericResponse(@RequestBody GenericRequest<? extends DataMarker> request) {
        LOG.info("getGenericResponse");
        if (request.getData() instanceof SimpleDataPayload) {
            SimpleDataPayload payload = (SimpleDataPayload) request.getData();
            return new GenericResponse<>(request.getName(), payload);
        } else if (request.getData() instanceof ComplexDataPayload) {
            ComplexDataPayload payload = (ComplexDataPayload) request.getData();
            return new GenericResponse<>(request.getName(), payload);
        } else {
            throw new UnsupportedOperationException("Unsupported type");
        }
    }

    @RequestMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestInfo> getRequestParameters(HttpServletRequest request) throws IOException {
        LOG.info("getRequestParameters: {}?{}", request.getRequestURL(), request.getQueryString());
        String body = request.getReader().lines().collect(Collectors.joining());
        RequestInfo requestInfo = new RequestInfo(applicationConfig.getId(), request.getRequestURL().toString(),
                request.getQueryString(), body, request.getCharacterEncoding(), request.getMethod(),
                createCookiesMap(request.getCookies()), request.getContentType(),  createHeaderMap(request),
                request.getProtocol(), createRemoteInfo(request));
        return ResponseEntity.ok(requestInfo);
    }

    private Map<String, String> createCookiesMap(Cookie[] cookies) {
        Map<String, String> cookiesMap = new HashMap<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String id = cookie.getDomain() + ":" + cookie.getName();
                cookiesMap.put(id, cookie.toString());
            }
        }
        return cookiesMap;
    }

    private Map<String, List<String>> createHeaderMap(HttpServletRequest request) {
        Map<String, List<String>> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                List<String> headerValues = new ArrayList<>();
                String headerName = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(headerName);
                while (values.hasMoreElements()) {
                    headerValues.add(values.nextElement());
                }
                headers.put(headerName, headerValues);
            }
        }
        return headers;
    }

    private String createRemoteInfo(HttpServletRequest request) {
        return request.getRemoteHost() + ":" + request.getRemotePort();
    }

}
