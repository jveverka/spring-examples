package itx.examples.springboot.redis.repository;

import itx.examples.springboot.redis.model.MessageData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<MessageData, String> {
}
