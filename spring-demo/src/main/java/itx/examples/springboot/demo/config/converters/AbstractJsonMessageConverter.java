package itx.examples.springboot.demo.config.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJsonMessageConverter {

    private static final List<MediaType> supportedMediaTypes;
    static {
        supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected List<MediaType> getMediaTypes() {
        return supportedMediaTypes;
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
