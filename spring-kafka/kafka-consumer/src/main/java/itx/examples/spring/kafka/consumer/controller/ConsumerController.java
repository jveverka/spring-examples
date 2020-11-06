package itx.examples.spring.kafka.consumer.controller;

import itx.examples.spring.kafka.consumer.service.MessageConsumer;
import itx.examples.spring.kafka.dto.DataMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/services")
public class ConsumerController {

    private final MessageConsumer messageConsumer;

    @Autowired
    public ConsumerController(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @GetMapping("/get-messages")
    public ResponseEntity<Collection<DataMessage>> sendMessage() {
        return ResponseEntity.ok(messageConsumer.getMessages());
    }

    @PutMapping("/reset")
    public ResponseEntity<Void> resetMessages() {
        messageConsumer.resetMessages();
        return ResponseEntity.ok().build();
    }

}
