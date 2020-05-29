package itx.examples.springboot.demo.dto.generic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class SimpleDataPayload implements DataMarker {

    private final String simpleData;

    @JsonCreator
    public SimpleDataPayload(@JsonProperty("simpleData") String simpleData) {
        this.simpleData = simpleData;
    }

    public String getSimpleData() {
        return simpleData;
    }

}
