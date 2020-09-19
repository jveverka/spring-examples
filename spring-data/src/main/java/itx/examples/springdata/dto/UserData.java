package itx.examples.springdata.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData {

    private final String id;
    private final String name;
    private final String email;
    private final Boolean enabled;

    @JsonCreator
    public UserData(@JsonProperty("id") String id,
                    @JsonProperty("name") String name,
                    @JsonProperty("email") String email,
                    @JsonProperty("enabled") Boolean enabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

}
