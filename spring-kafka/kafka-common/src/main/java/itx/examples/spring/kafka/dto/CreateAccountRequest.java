package itx.examples.spring.kafka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAccountRequest {

    private final String name;
    private final Integer credit;

    @JsonCreator
    public CreateAccountRequest(@JsonProperty("name") String name, @JsonProperty("credit") Integer credit) {
        this.name = name;
        this.credit = credit;
    }

    public String getName() {
        return name;
    }

    public Integer getCredit() {
        return credit;
    }

}
