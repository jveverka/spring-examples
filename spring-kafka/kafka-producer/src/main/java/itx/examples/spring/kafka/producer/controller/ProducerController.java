package itx.examples.spring.kafka.producer.controller;

import itx.examples.spring.kafka.dto.DataMessage;
import itx.examples.spring.kafka.dto.MessageRequest;
import itx.examples.spring.kafka.producer.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/services")
public class ProducerController {

    private final MessageProducer messageProducer;

    @Autowired
    public ProducerController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/send-message")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequest message) {
        messageProducer.sendMessage(new DataMessage(UUID.randomUUID().toString(), message.getMessage()));
        return ResponseEntity.ok().build();
    }

}
