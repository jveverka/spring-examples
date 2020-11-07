package itx.examples.spring.kafka.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "typeId"
)
public class DataMessageEvent implements EventMarker {

    private final String id;
    private final String message;

    @JsonCreator
    public DataMessageEvent(@JsonProperty("id") String id,
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
