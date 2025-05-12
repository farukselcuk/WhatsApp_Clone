package tr.com.ind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.ind.model.GroupMessage;


public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {

}

