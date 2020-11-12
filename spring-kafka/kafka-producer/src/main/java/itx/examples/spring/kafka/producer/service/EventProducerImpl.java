package itx.examples.spring.kafka.producer.service;

import itx.examples.spring.kafka.events.AccountAsyncEvent;
import itx.examples.spring.kafka.events.DataMessageAsyncEvent;
import itx.examples.spring.kafka.events.AsyncEvent;
import itx.examples.spring.kafka.events.EventWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class EventProducerImpl implements EventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventProducerImpl.class);
    private static final String TOPIC = "prod-con-test-topic";

    private final KafkaTemplate<String, EventWrapper<? extends AsyncEvent>> kafkaTemplate;

    public EventProducerImpl(@Autowired KafkaTemplate<String, EventWrapper<? extends AsyncEvent>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(DataMessageAsyncEvent message) throws EventPublishException {
        try {
            String messageKey = message.getId();
            LOGGER.info("#### Producing message: {} {}", message.getId(), message.getMessage());
            ListenableFuture<SendResult<String, EventWrapper<? extends AsyncEvent>>> send = this.kafkaTemplate.send(TOPIC, messageKey, new EventWrapper<>(message));
            send.get();
        } catch (Exception e) {
            throw new EventPublishException("Kafka publish DataMessage exception", e);
        }
    }

    @Override
    public void sendAccountMessage(AccountAsyncEvent accountAsyncEvent) throws EventPublishException {
        try {
            String messageKey = accountAsyncEvent.keyMessageKey();
            LOGGER.info("#### Producing account message: {}", accountAsyncEvent.getId());
            this.kafkaTemplate.send(TOPIC, messageKey, new EventWrapper<>(accountAsyncEvent));
        } catch (Exception e) {
            throw new EventPublishException("Kafka publish AccountEvent exception", e);
        }
    }

}
