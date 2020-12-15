package itx.examples.springboot.redis.controller;

import itx.examples.springboot.redis.model.MessageData;
import itx.examples.springboot.redis.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/services/data")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<Collection<MessageData>> getMessages() {
        return ResponseEntity.ok(messageService.getAll());
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<MessageData> getMessageById(@PathVariable("id") String id) {
        return ResponseEntity.of(messageService.get(id));
    }

    @PostMapping("/messages/{message}")
    public ResponseEntity<MessageData> save(@PathVariable("message") String message) {
        MessageData msg = new MessageData(UUID.randomUUID().toString(), message);
        messageService.save(msg);
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<MessageData> delete(@PathVariable("id") String id) {
        return ResponseEntity.of(messageService.delete(id));
    }

}
