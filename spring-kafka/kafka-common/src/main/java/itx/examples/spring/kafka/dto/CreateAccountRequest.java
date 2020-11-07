package itx.examples.spring.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAccountRequest {

    private final Long credit;

    @JsonCreator
    public CreateAccountRequest(@JsonProperty("credit") Long credit) {
        this.credit = credit;
    }

    public Long getCredit() {
        return credit;
    }

}
