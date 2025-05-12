package tr.com.ind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.ind.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
