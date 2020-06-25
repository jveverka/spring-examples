package itx.examples.springboot.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataMessage {

    private final String data;

    @JsonCreator
    public DataMessage(@JsonProperty("data") String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

}
