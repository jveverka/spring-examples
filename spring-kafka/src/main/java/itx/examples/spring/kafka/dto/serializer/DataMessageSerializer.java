package itx.examples.spring.kafka.dto.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.spring.kafka.dto.DataMessage;
import org.apache.kafka.common.serialization.Serializer;

public class DataMessageSerializer implements Serializer<DataMessage> {

    private final ObjectMapper mapper;

    public DataMessageSerializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, DataMessage data) {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
