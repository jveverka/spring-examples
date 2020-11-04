package itx.examples.spring.kafka.controller;

import itx.examples.spring.kafka.dto.DataMessage;
import itx.examples.spring.kafka.dto.MessageReply;
import itx.examples.spring.kafka.dto.MessageRequest;
import itx.examples.spring.kafka.services.MessageConsumer;
import itx.examples.spring.kafka.services.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@RestController
@RequestMapping("/services")
public class DataController {

    private final MessageProducer messageProducer;
    private final MessageConsumer messageConsumer;

    @Autowired
    public DataController(MessageProducer messageProducer, MessageConsumer messageConsumer) {
        this.messageProducer = messageProducer;
        this.messageConsumer = messageConsumer;
    }

    @PostMapping("/send-message")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequest message) {
        messageProducer.sendMessage(new DataMessage(UUID.randomUUID().toString(), message.getMessage()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-message-and-get-response")
    public ResponseEntity<MessageReply> sendMessageAndGetResponse(@RequestBody MessageRequest message) throws ExecutionException, InterruptedException {
        long timestamp = System.nanoTime();
        DataMessage dataMessage = new DataMessage(UUID.randomUUID().toString(), message.getMessage());
        Future<DataMessage> future = messageConsumer.getFuture(dataMessage.getId());
        messageProducer.sendMessage(dataMessage);
        DataMessage reply = future.get();
        float duration = (System.nanoTime() - timestamp)/(1_000_000F);
        return ResponseEntity.ok(new MessageReply(reply.getMessage(), duration));
    }

}
