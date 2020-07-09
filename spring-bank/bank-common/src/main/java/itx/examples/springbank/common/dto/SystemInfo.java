package itx.examples.springbank.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemInfo {

    private final String version;

    @JsonCreator
    public SystemInfo(@JsonProperty("version") String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

}
