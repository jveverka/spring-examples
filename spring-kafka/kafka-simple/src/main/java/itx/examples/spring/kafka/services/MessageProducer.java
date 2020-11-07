package itx.examples.spring.kafka.services;

import itx.examples.spring.kafka.events.DataMessageEvent;
import itx.examples.spring.kafka.events.EventWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);
    private static final String TOPIC = "service-requests";

    private final KafkaTemplate<String, EventWrapper<DataMessageEvent>> kafkaTemplate;

    public MessageProducer(@Autowired KafkaTemplate<String, EventWrapper<DataMessageEvent>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(EventWrapper<DataMessageEvent> message) {
        LOGGER.info("#### Producing message: {} {}", message.getEvent().getId(), message.getEvent().getMessage());
        this.kafkaTemplate.send(TOPIC, message);
    }

}
