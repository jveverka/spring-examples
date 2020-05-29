package itx.examples.springboot.oauth2rs.controller;

public class ServerData {

    private final String data;

    public ServerData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

}
