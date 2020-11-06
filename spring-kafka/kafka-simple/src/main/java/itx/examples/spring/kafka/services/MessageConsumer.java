package itx.examples.spring.kafka.services;

import itx.examples.spring.kafka.dto.DataMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Service
public class MessageConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final Map<String, CompletableFuture<DataMessage>> counters;

    public MessageConsumer() {
        this.counters = new ConcurrentHashMap<>();
    }

    public Future<DataMessage> getFuture(String id) {
        CompletableFuture future  = new CompletableFuture();
        counters.put(id, future);
        return future;
    }

    @KafkaListener(topics = "service-requests", groupId = "the-group")
    public void consume(DataMessage message) throws IOException {
        LOGGER.info("#### Consumed message -> {} {}", message.getId(), message.getMessage());
        CompletableFuture<DataMessage> future = counters.remove(message.getId());
        if (future != null) {
            future.complete(message);
        }
    }

}
