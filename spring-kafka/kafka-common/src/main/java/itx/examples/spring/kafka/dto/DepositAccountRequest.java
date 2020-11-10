package itx.examples.spring.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DepositAccountRequest {

    private final String accountId;
    private final Integer credit;

    @JsonCreator
    public DepositAccountRequest(@JsonProperty("accountId") String accountId,
                                 @JsonProperty("credit") Integer credit) {
        this.accountId = accountId;
        this.credit = credit;
    }

    public String getAccountId() {
        return accountId;
    }

    public Integer getCredit() {
        return credit;
    }

}
