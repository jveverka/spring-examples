package itx.examples.spring.kafka.service;

import itx.examples.spring.kafka.events.AsyncEvent;
import itx.examples.spring.kafka.events.CreateAccountAsyncEvent;
import itx.examples.spring.kafka.events.DataMessageAsyncEvent;
import itx.examples.spring.kafka.events.DeleteAccountAsyncEvent;
import itx.examples.spring.kafka.events.DepositAccountAsyncEvent;
import itx.examples.spring.kafka.events.EventWrapper;
import itx.examples.spring.kafka.events.TransferFundsAsyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Map<String, DataMessageAsyncEvent> messages;

    @Autowired
    public EventConsumer() {
        this.messages = new ConcurrentHashMap<>();
    }

    public Collection<DataMessageAsyncEvent> getMessages() {
        return Collections.unmodifiableCollection(this.messages.values());
    }

    public void resetMessages() {
        this.messages.clear();
    }

    @KafkaListener(topics = "sync-prod-con-responses")
    public void consume(EventWrapper<? extends AsyncEvent> message) throws IOException {
        LOGGER.info("#### Consumed message -> {} {}", message.getType());
        if (message.getType().equals(DataMessageAsyncEvent.class)) {
            EventWrapper<DataMessageAsyncEvent> wrapper = (EventWrapper<DataMessageAsyncEvent>)message;
            messages.put(wrapper.getEvent().getId(), wrapper.getEvent());
        } else {
            LOGGER.error("ERROR: unsupported message type {}", message.getType());
        }
    }

}
