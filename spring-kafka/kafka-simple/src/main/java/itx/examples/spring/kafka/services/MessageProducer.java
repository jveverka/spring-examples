package itx.examples.spring.kafka.services;

import itx.examples.spring.kafka.dto.DataMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);
    private static final String TOPIC = "service-requests";

    private final KafkaTemplate<String, DataMessage> kafkaTemplate;

    public MessageProducer(@Autowired KafkaTemplate<String, DataMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(DataMessage message) {
        LOGGER.info("#### Producing message: {} {}", message.getId(), message.getMessage());
        this.kafkaTemplate.send(TOPIC, message);
    }

}
