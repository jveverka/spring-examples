package itx.examples.springdata.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserId {

    private final String id;

    @JsonCreator
    public UserId(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
