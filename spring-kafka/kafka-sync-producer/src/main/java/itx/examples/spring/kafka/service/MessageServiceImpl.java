package itx.examples.spring.kafka.service;

import itx.examples.spring.kafka.dto.MessageReply;
import itx.examples.spring.kafka.dto.MessageRequest;
import itx.examples.spring.kafka.events.TxMessageAsyncEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class MessageServiceImpl implements MessageService {

    private final EventProducer eventProducer;
    private final EventConsumer eventConsumer;

    @Autowired
    public MessageServiceImpl(EventProducer eventProducer, EventConsumer eventConsumer) {
        this.eventProducer = eventProducer;
        this.eventConsumer = eventConsumer;
    }

    @Override
    public Future<MessageReply> sendMessage(MessageRequest request) {
        String txId = UUID.randomUUID().toString();
        CompletableFuture<MessageReply> completableFuture = new CompletableFuture<>();
        TxMessageAsyncEvent txMessageAsyncEvent = new TxMessageAsyncEvent(request.getId(), txId, request.getMessage());
        eventConsumer.setTransaction(txId, completableFuture);
        eventProducer.sendMessage(txMessageAsyncEvent);
        return completableFuture;
    }

}
