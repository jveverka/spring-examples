package itx.examples.springboot.security.services.dto;

public class ServerData {

    private final String source;
    private final String data;
    private final long timestamp;
    private final String state;

    public ServerData(String source, String data, long timestamp, String state) {
        this.source = source;
        this.data = data;
        this.timestamp = timestamp;
        this.state = state;
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

    public String getState() {
        return state;
    }
}
