package itx.examples.spring.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    private final String accountId;
    private final String name;
    private final Integer credit;

    @JsonCreator
    public Account(@JsonProperty("accountId") String accountId,
                   @JsonProperty("name") String name,
                   @JsonProperty("credit") Integer credit) {
        this.accountId = accountId;
        this.name = name;
        this.credit = credit;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public Integer getCredit() {
        return credit;
    }
}
