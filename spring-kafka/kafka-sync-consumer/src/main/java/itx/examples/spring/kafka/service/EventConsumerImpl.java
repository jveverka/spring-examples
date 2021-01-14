package itx.examples.spring.kafka.service;

import itx.examples.spring.kafka.events.AsyncEvent;
import itx.examples.spring.kafka.events.EventWrapper;
import itx.examples.spring.kafka.events.TxMessageAsyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;


@Service
public class EventConsumerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumerImpl.class);
    private static final String TOPIC = "sync-prod-con-responses";

    private final KafkaTemplate<String, EventWrapper<? extends AsyncEvent>> kafkaTemplate;

    public EventConsumerImpl(@Autowired KafkaTemplate<String, EventWrapper<? extends AsyncEvent>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "sync-prod-con-requests")
    public void consume(EventWrapper<? extends AsyncEvent> message) {
        LOGGER.info("#### Consumed message -> {}", message.getType());
        if (message.getType().equals(TxMessageAsyncEvent.class)) {
            EventWrapper<TxMessageAsyncEvent> wrapper = (EventWrapper<TxMessageAsyncEvent>)message;
            TxMessageAsyncEvent txMessageAsyncEvent = wrapper.getEvent();
            sendMessage(txMessageAsyncEvent);
        } else {
            LOGGER.error("ERROR: unsupported message type {}", message.getType());
        }
    }

    public void sendMessage(TxMessageAsyncEvent message) throws EventPublishException {
        try {
            String messageKey = message.getId();
            LOGGER.info("#### Producing message: {} {}", message.getId(), message.getMessage());
            ListenableFuture<SendResult<String, EventWrapper<? extends AsyncEvent>>> send = this.kafkaTemplate.send(TOPIC, messageKey, new EventWrapper<>(message));
            send.get();
        } catch (Exception e) {
            throw new EventPublishException("Kafka publish DataMessage exception", e);
        }
    }

}
