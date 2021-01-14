package itx.examples.spring.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageReply {

    private final String id;
    private final String message;
    private final Float duration;

    @JsonCreator
    public MessageReply(@JsonProperty("id") String id,
                        @JsonProperty("message") String message,
                        @JsonProperty("duration") Float duration) {
        this.id = id;
        this.message = message;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Float getDuration() {
        return duration;
    }

}
