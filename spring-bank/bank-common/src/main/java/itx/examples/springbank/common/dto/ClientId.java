package itx.examples.springbank.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ClientId {

    private final String id;

    @JsonCreator
    public ClientId(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientId clientId = (ClientId) o;
        return Objects.equals(id, clientId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
