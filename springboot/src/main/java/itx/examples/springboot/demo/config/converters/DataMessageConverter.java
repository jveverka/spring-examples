package itx.examples.springboot.demo.config.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.springboot.demo.dto.DataMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DataMessageConverter extends AbstractJsonMessageConverter implements HttpMessageConverter<DataMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(DataMessageConverter.class);

    public DataMessageConverter() {
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        LOG.info("canRead");
        if (DataMessage.class.equals(clazz) &&
                (MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_JSON_UTF8.equals(mediaType))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        LOG.info("canWrite");
        if (DataMessage.class.equals(clazz) && (
                MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_JSON_UTF8.equals(mediaType))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        LOG.info("getSupportedMediaTypes");
        return getMediaTypes();
    }

    @Override
    public DataMessage read(Class<? extends DataMessage> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        LOG.info("read");
        ObjectMapper objectMapper = getObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(inputMessage.getBody());
        String data = jsonNode.get("data").textValue();
        return new DataMessage(data);
    }

    @Override
    public void write(DataMessage dataMessage, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        LOG.info("write");
        ObjectMapper objectMapper = getObjectMapper();
        OutputStream stream = outputMessage.getBody();
        objectMapper.writeValue(stream, dataMessage);
        stream.flush();
    }

}
