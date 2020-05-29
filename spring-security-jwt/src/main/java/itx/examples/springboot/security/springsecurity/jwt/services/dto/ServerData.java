package itx.examples.springboot.security.springsecurity.jwt.services.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerData {

    private final String source;
    private final String data;
    private final long timestamp;

    @JsonCreator
    public ServerData(@JsonProperty("source") String source,
                      @JsonProperty("data") String data,
                      @JsonProperty("timestamp") long timestamp) {
        this.source = source;
        this.data = data;
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSource() {
        return source;
    }
}
