package itx.examples.springboot.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class RequestInfo {

    private final String appId;
    private final String url;
    private final String queryString;
    private final String body;
    private final String encoding;
    private final String method;
    private final Map<String, String> cookies;
    private final String contentType;
    private final Map<String, List<String>> headers;
    private final String protocol;
    private final String remoteInfo;

    @JsonCreator
    public RequestInfo(@JsonProperty("appId") String appId,
                       @JsonProperty("url") String url,
                       @JsonProperty("queryString") String queryString,
                       @JsonProperty("body") String body,
                       @JsonProperty("encoding") String encoding,
                       @JsonProperty("method") String method,
                       @JsonProperty("cookies") Map<String, String> cookies,
                       @JsonProperty("contentType") String contentType,
                       @JsonProperty("headers") Map<String, List<String>> headers,
                       @JsonProperty("protocol") String protocol,
                       @JsonProperty("remoteInfo") String remoteInfo) {
        this.appId = appId;
        this.url = url;
        this.queryString = queryString;
        this.body = body;
        this.encoding = encoding;
        this.method = method;
        this.cookies = cookies;
        this.contentType = contentType;
        this.headers = headers;
        this.protocol = protocol;
        this.remoteInfo = remoteInfo;
    }

    public String getAppId() {
        return appId;
    }

    public String getUrl() {
        return url;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getBody() {
        return body;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getContentType() {
        return contentType;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getRemoteInfo() {
        return remoteInfo;
    }

}
