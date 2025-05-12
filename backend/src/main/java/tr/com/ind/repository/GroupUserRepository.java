package tr.com.ind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.ind.model.GroupUser;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
}
