package itx.examples.springbank.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawRequest extends TransferRequest {

    @JsonCreator
    public WithdrawRequest(@JsonProperty("targetAccountId") AccountId targetAccountId,
                           @JsonProperty("amount") Long amount) {
        super(targetAccountId, amount);
    }

}
