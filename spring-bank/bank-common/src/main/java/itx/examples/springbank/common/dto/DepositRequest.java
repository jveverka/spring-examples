package itx.examples.springbank.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DepositRequest extends TransferRequest {

    @JsonCreator
    public DepositRequest(@JsonProperty("targetAccountId") AccountId targetAccountId,
                          @JsonProperty("amount") Long amount) {
        super(targetAccountId, amount);
    }

}
