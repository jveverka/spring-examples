package itx.examples.spring.kafka.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "typeId"
)
public class TxMessageAsyncEvent implements AsyncEvent {

    private final String id;
    private final String txId;
    private final String message;

    @JsonCreator
    public TxMessageAsyncEvent(@JsonProperty("id") String id,
                               @JsonProperty("txId") String txId,
                               @JsonProperty("message") String message) {
        this.id = id;
        this.txId = txId;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getTxId() {
        return txId;
    }

}
