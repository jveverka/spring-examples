package itx.examples.springbank.common.dto;

public class SystemInfo {

    private final String version;

    public SystemInfo(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

}
