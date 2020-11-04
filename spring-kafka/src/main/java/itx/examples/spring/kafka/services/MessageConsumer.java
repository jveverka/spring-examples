package itx.examples.spring.kafka.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @KafkaListener(topics = "service-requests", groupId = "group_id")
    public void consume(String message) throws IOException {
        LOGGER.info(String.format("#### -> Consumed message -> %s", message));
    }

}
