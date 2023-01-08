package itx.examples.springmongo.service;

import itx.examples.springmongo.dto.CreateRequest;
import itx.examples.springmongo.dto.Message;
import itx.examples.springmongo.dto.MessageId;

import java.util.List;

public interface MessageService {

    List<Message> getAll();
    MessageId saveMessage(CreateRequest request);

    void delete(MessageId id);

}
