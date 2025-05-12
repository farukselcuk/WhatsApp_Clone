package tr.com.ind.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.ind.exception.UserNotFoundException;
import tr.com.ind.model.User;
import tr.com.ind.model.UserFriend;
import tr.com.ind.repository.UserFriendRepository;
import tr.com.ind.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserFriendDtoConverter {
    private final UserFriendRepository userFriendRepository;
    private final UserRepository userRepository;

    public UserFriendDto toDto(Long friendId) {
        User friend = userRepository.findById(friendId).orElseThrow(
                () -> new UserNotFoundException("Friend not found with id: " + friendId)
        );
        return new UserFriendDto(
                friend.getName(),
                friend.getSurname(),
                friend.getPhone(),
                friend.getPhoto(),
                friend.getStatus()
        );
    }

    public List<UserFriendDto> toDto(List<Long> friendIds) {
        return friendIds.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<UserFriendDto> getFriends(Long userId) {

        List<UserFriend> activeFriendsAsUser = userFriendRepository.findByUserIdAndIsActiveTrue(userId);
        List<UserFriend> activeFriendsAsFriend = userFriendRepository.findByFriendIdAndIsActiveTrue(userId);

        if (activeFriendsAsUser.isEmpty() && activeFriendsAsFriend.isEmpty()) {
            throw new UserNotFoundException("No active friends found");
        }

        List<Long> activeFriendIdsAsUser = activeFriendsAsUser.stream()
                .map(UserFriend::getFriendId)
                .collect(Collectors.toList());

        List<Long> activeFriendIdsAsFriend = activeFriendsAsFriend.stream()
                .map(UserFriend::getUserId)
                .collect(Collectors.toList());


        List<Long> allActiveFriendIds = new ArrayList<>();
        allActiveFriendIds.addAll(activeFriendIdsAsUser);
        allActiveFriendIds.addAll(activeFriendIdsAsFriend);

        return toDto(allActiveFriendIds);
    }
}
