package itx.examples.spring.kafka.consumer.service;

import itx.examples.spring.kafka.events.DataMessageEvent;
import itx.examples.spring.kafka.events.EventMarker;
import itx.examples.spring.kafka.events.EventWrapper;
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
public class EventConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

    private final Map<String, DataMessageEvent> messages;

    public EventConsumer() {
        this.messages = new ConcurrentHashMap<>();
    }

    public Collection<DataMessageEvent> getMessages() {
        return Collections.unmodifiableCollection(this.messages.values());
    }

    public void resetMessages() {
        this.messages.clear();
    }

    @KafkaListener(topics = "request-test-topic", groupId = "the-group")
    public void consume(EventWrapper<? extends EventMarker> message) throws IOException {
        LOGGER.info("#### Consumed message -> {} {}", message.getType());
        if (message.getType().equals(DataMessageEvent.class)) {
            EventWrapper<DataMessageEvent> wrapper = (EventWrapper<DataMessageEvent>)message;
            messages.put(wrapper.getEvent().getId(), wrapper.getEvent());
        }
    }

}
