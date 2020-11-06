package itx.examples.spring.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataMessage {

    private final String id;
    private final String message;

    @JsonCreator
    public DataMessage(@JsonProperty("id") String id,
                       @JsonProperty("message") String message) {
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }
}
