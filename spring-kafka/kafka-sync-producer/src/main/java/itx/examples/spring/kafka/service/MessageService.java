package itx.examples.spring.kafka.service;

import itx.examples.spring.kafka.dto.MessageReply;
import itx.examples.spring.kafka.dto.MessageRequest;

import java.util.concurrent.Future;

public interface MessageService {

    Future<MessageReply> sendMessage(MessageRequest request);

}
