package itx.examples.springboot.redis.service;

import itx.examples.springboot.redis.model.MessageData;
import itx.examples.springboot.redis.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;

    @Autowired
    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<MessageData> getAll() {
        Iterable<MessageData> messages = repository.findAll();
        return StreamSupport
                .stream(messages.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MessageData> get(String id) {
        return repository.findById(id);
    }

    @Override
    public void save(MessageData message) {
        repository.save(message);
    }

    @Override
    public Optional<MessageData> delete(String id) {
        Optional<MessageData> message = repository.findById(id);
        if (message.isPresent()) {
            repository.delete(message.get());
            return message;
        } else {
            return Optional.empty();
        }
    }
}
