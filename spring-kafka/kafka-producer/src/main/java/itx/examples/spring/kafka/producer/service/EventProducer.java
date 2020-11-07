package itx.examples.spring.kafka.producer.service;

import itx.examples.spring.kafka.events.DataMessageEvent;
import itx.examples.spring.kafka.events.EventMarker;
import itx.examples.spring.kafka.events.EventWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);
    private static final String TOPIC = "request-test-topic";

    private final KafkaTemplate<String, EventWrapper<? extends EventMarker>> kafkaTemplate;

    public EventProducer(@Autowired KafkaTemplate<String, EventWrapper<? extends EventMarker>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(DataMessageEvent message) {
        LOGGER.info("#### Producing message: {} {}", message.getId(), message.getMessage());
        this.kafkaTemplate.send(TOPIC, new EventWrapper<>(message));
    }

}
