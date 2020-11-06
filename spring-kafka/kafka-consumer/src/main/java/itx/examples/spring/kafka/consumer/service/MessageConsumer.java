package itx.examples.spring.kafka.consumer.service;

import itx.examples.spring.kafka.dto.DataMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final Map<String, DataMessage> messages;

    public MessageConsumer() {
        this.messages = new ConcurrentHashMap<>();
    }

    public Collection<DataMessage> getMessages() {
        return Collections.unmodifiableCollection(this.messages.values());
    }

    public void resetMessages() {
        this.messages.clear();
    }

    @KafkaListener(topics = "request-test-topic", groupId = "the-group")
    public void consume(DataMessage message) throws IOException {
        LOGGER.info("#### Consumed message -> {} {}", message.getId(), message.getMessage());
        messages.put(message.getId(), message);
    }

}
