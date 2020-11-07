package itx.examples.spring.kafka.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "typeId"
)
public class DeleteAccountEvent implements EventMarker {

    private final String id;
    private final String accountId;

    @JsonCreator
    public DeleteAccountEvent(@JsonProperty("id") String id,
                              @JsonProperty("accountId") String accountId) {
        this.id = id;
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

}
