package itx.examples.spring.kafka.consumer.controller;

import itx.examples.spring.kafka.consumer.service.EventConsumer;
import itx.examples.spring.kafka.events.DataMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/services/messages")
public class MessageController {

    private final EventConsumer eventConsumer;

    @Autowired
    public MessageController(EventConsumer eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    @GetMapping()
    public ResponseEntity<Collection<DataMessageEvent>> sendMessage() {
        return ResponseEntity.ok(eventConsumer.getMessages());
    }

    @PutMapping()
    public ResponseEntity<Void> resetMessages() {
        eventConsumer.resetMessages();
        return ResponseEntity.ok().build();
    }

}
