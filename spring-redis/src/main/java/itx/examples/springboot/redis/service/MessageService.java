package itx.examples.springboot.redis.service;

import itx.examples.springboot.redis.model.MessageData;

import java.util.Collection;
import java.util.Optional;

public interface MessageService {

    Collection<MessageData> getAll();

    Optional<MessageData> get(String id);

    void save(MessageData message);

    Optional<MessageData> delete(String id);

}
