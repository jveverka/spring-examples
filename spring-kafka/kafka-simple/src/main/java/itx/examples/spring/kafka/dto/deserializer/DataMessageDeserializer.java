package itx.examples.spring.kafka.dto.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.spring.kafka.dto.DataMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class DataMessageDeserializer implements Deserializer<DataMessage> {

    private final ObjectMapper mapper;

    public DataMessageDeserializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public DataMessage deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, DataMessage.class);
        } catch (IOException e) {
            return null;
        }
    }

}
