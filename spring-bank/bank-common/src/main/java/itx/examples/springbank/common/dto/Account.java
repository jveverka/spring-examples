package itx.examples.springbank.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    private final AccountId id;
    private final Long credit;

    @JsonCreator
    public Account(@JsonProperty("id") AccountId id,
                   @JsonProperty("credit") Long credit) {
        this.id = id;
        this.credit = credit;
    }

    public Long getCredit() {
        return credit;
    }

    public AccountId getId() {
        return id;
    }

}
