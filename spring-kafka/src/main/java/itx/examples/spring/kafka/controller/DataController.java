package itx.examples.spring.kafka.controller;

import itx.examples.spring.kafka.dto.DataMessage;
import itx.examples.spring.kafka.services.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/services")
public class DataController {

    private final MessageProducer messageProducer;

    @Autowired
    public DataController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/message")
    public ResponseEntity<DataMessage> sendMessage(@RequestBody DataMessage message) {
        messageProducer.sendMessage(message.getMessage());
        return ResponseEntity.ok().build();
    }

}
