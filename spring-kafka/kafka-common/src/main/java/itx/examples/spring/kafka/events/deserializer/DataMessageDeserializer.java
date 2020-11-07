package itx.examples.spring.kafka.events.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.spring.kafka.events.EventWrapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class DataMessageDeserializer implements Deserializer<EventWrapper> {

    private final ObjectMapper mapper;

    public DataMessageDeserializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public EventWrapper deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, EventWrapper.class);
        } catch (IOException e) {
            return null;
        }
    }

}
