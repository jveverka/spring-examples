package itx.examples.springbank.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionRequest extends TransferRequest {

    private final AccountId sourceAccountId;

    @JsonCreator
    public TransactionRequest(@JsonProperty("sourceAccountId") AccountId sourceAccountId,
                              @JsonProperty("targetAccountId") AccountId targetAccountId,
                              @JsonProperty("amount") Long amount) {
        super(targetAccountId, amount);
        this.sourceAccountId = sourceAccountId;
    }

    public AccountId getSourceAccountId() {
        return sourceAccountId;
    }

}
