package itx.examples.spring.kafka.controller;

import itx.examples.spring.kafka.dto.MessageReply;
import itx.examples.spring.kafka.dto.MessageRequest;
import itx.examples.spring.kafka.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/services/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping()
    public ResponseEntity<MessageReply> sendMessage(@RequestBody MessageRequest message) throws ExecutionException, InterruptedException {
        Future<MessageReply> messageReplyFuture = messageService.sendMessage(message);
        return ResponseEntity.ok(messageReplyFuture.get());
    }

}
