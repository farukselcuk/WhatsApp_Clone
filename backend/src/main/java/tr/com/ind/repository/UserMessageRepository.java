package tr.com.ind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.ind.model.UserMessage;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {

}

