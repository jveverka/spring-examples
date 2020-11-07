package itx.examples.spring.kafka.events.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.spring.kafka.events.EventWrapper;
import org.apache.kafka.common.serialization.Serializer;

public class DataMessageSerializer implements Serializer<EventWrapper> {

    private final ObjectMapper mapper;

    public DataMessageSerializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, EventWrapper data) {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
