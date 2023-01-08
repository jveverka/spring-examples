package itx.examples.springmongo.service.impl;

import itx.examples.springmongo.dto.CreateRequest;
import itx.examples.springmongo.dto.Message;
import itx.examples.springmongo.dto.MessageId;
import itx.examples.springmongo.repository.MessageDocument;
import itx.examples.springmongo.repository.MessageRepository;
import itx.examples.springmongo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getAll() {
        List<Message> result = new ArrayList<>();
        messageRepository.findAll().forEach(m -> {
            result.add(new Message(m.getId(), m.getMessage()));
        });
        return result;
    }

    @Override
    public MessageId saveMessage(CreateRequest request) {
        MessageDocument saved = messageRepository.save(new MessageDocument(null, request.message()));
        return new MessageId(saved.getId());
    }

    @Override
    public void delete(MessageId id) {
        messageRepository.deleteById(id.id());
    }

}
