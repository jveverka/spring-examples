package itx.examples.springmongo.repository;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageDocument, String> {
}
