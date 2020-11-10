package itx.examples.spring.kafka.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "typeId"
)
public class DeleteAccountAsyncEvent implements AccountAsyncEvent {

    private final String id;
    private final String accountId;

    @JsonCreator
    public DeleteAccountAsyncEvent(@JsonProperty("id") String id,
                                   @JsonProperty("accountId") String accountId) {
        this.id = id;
        this.accountId = accountId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String keyMessageKey() {
        return accountId;
    }

    public String getAccountId() {
        return accountId;
    }

}
