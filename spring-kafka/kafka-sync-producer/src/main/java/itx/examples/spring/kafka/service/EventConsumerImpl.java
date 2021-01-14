package itx.examples.spring.kafka.service;

import itx.examples.spring.kafka.dto.MessageReply;
import itx.examples.spring.kafka.dto.TxContext;
import itx.examples.spring.kafka.events.AsyncEvent;
import itx.examples.spring.kafka.events.EventWrapper;
import itx.examples.spring.kafka.events.TxMessageAsyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventConsumerImpl implements EventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumerImpl.class);

    private final Map<String, TxContext> processing;

    @Autowired
    public EventConsumerImpl() {
        this.processing = new ConcurrentHashMap<>();
    }

    @KafkaListener(topics = "sync-prod-con-responses")
    public void consume(EventWrapper<? extends AsyncEvent> message) throws IOException {
        LOGGER.info("#### Consumed message -> {}", message.getType());
        if (message.getType().equals(TxMessageAsyncEvent.class)) {
            EventWrapper<TxMessageAsyncEvent> wrapper = (EventWrapper<TxMessageAsyncEvent>)message;
            TxMessageAsyncEvent txMessageAsyncEvent = wrapper.getEvent();
            TxContext txContext = processing.get(txMessageAsyncEvent.getTxId());
            if (txContext != null) {
                Float duration = (System.nanoTime() - txContext.getStarted())/1_000_000F;
                MessageReply reply = new MessageReply(txMessageAsyncEvent.getId(), txMessageAsyncEvent.getMessage(), duration);
                txContext.getCompletableFuture().complete(reply);
            }
        } else {
            LOGGER.error("ERROR: unsupported message type {}", message.getType());
        }
    }

    @Override
    public void setTransaction(String txId, CompletableFuture<MessageReply> completableFuture) {
        processing.put(txId, new TxContext(System.nanoTime(), completableFuture));
    }

}
