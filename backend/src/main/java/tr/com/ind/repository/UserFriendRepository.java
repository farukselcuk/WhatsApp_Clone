package tr.com.ind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.ind.model.UserFriend;

import java.util.List;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {
    List<UserFriend> findByUserIdAndIsActiveTrue(Long userId);
    List<UserFriend> findByFriendIdAndIsActiveTrue(Long userId);
    UserFriend findByUserIdAndFriendIdAndIsActiveTrue(Long userId, Long friendId);
}
