package itx.examples.springbank.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class TransferRequest {

    private final AccountId targetAccountId;
    private final Long amount;

    @JsonCreator
    public TransferRequest(@JsonProperty("targetAccountId") AccountId targetAccountId,
                           @JsonProperty("amount") Long amount) {
        this.targetAccountId = targetAccountId;
        this.amount = amount;
    }

    public AccountId getTargetAccountId() {
        return targetAccountId;
    }

    public Long getAmount() {
        return amount;
    }

}
