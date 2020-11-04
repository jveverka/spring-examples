package itx.examples.spring.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataMessage {

    private final String message;

    @JsonCreator
    public DataMessage(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
