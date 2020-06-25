package itx.examples.springboot.demo.dto.generic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericResponse<T extends DataMarker> {

    private final String name;
    private final T data;

    @JsonCreator
    public GenericResponse(@JsonProperty("name") String name,
                           @JsonProperty("data") T data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public T getData() {
        return data;
    }
}
