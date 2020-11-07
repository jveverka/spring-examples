package itx.examples.spring.kafka.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "typeId"
)
public class TransferFundsEvent implements EventMarker {

    private final String id;
    private final String sourceAccountId;
    private final String destinationAccountId;
    private final Integer credit;

    @JsonCreator
    public TransferFundsEvent(@JsonProperty("id") String id,
                              @JsonProperty("sourceAccountId") String sourceAccountId,
                              @JsonProperty("destinationAccountId") String destinationAccountId,
                              @JsonProperty("credit") Integer credit) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public Integer getCredit() {
        return credit;
    }

}
