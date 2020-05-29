package itx.examples.springboot.demo.dto.generic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class ComplexDataPayload implements DataMarker {

    private final String complexData;

    @JsonCreator
    public ComplexDataPayload(@JsonProperty("complexData") String complexData) {
        this.complexData = complexData;
    }

    public String getComplexData() {
        return complexData;
    }

}
