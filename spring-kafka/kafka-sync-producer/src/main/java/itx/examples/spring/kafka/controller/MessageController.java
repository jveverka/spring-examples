package itx.examples.spring.kafka.controller;

import itx.examples.spring.kafka.dto.MessageRequest;
import itx.examples.spring.kafka.events.DataMessageAsyncEvent;
import itx.examples.spring.kafka.service.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/services/messages")
public class MessageController {

    private final EventProducer eventProducer;

    @Autowired
    public MessageController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @PostMapping()
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequest message) {
        eventProducer.sendMessage(new DataMessageAsyncEvent(UUID.randomUUID().toString(), message.getMessage()));
        return ResponseEntity.ok().build();
    }

}
