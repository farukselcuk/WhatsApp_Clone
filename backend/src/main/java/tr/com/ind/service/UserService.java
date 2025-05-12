package tr.com.ind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tr.com.ind.dto.*;
import tr.com.ind.exception.*;
import tr.com.ind.model.User;
import tr.com.ind.model.UserFriend;
import tr.com.ind.repository.UserFriendRepository;
import tr.com.ind.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserFriendRepository userFriendRepository;
    private final UserFriendDtoConverter userFriendDtoConverter;

    public UserDto createUser(UserRegisterRequest request) {
        try {
            userDtoConverter.createUserToken(request);
            return userDtoConverter.createUser(request);
        } catch (Exception e) {
            throw new UserCreationException("Failed to create user");
        }
    }

    public UserTokenResponse authenticate(UserLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByPhone(request.getPhone()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return UserTokenResponse.builder()
                .token(jwtToken)
                .build();
    }

    public List<UserDto> getAllUsers() {
        return userDtoConverter.convertUserDto(userRepository.findAll());
    }

    public UserDto getUserByPhone(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("User with phone " + phone + " not found"));
        return userDtoConverter.convertUserDto(user);
    }
    public User findUserByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new BadRequestexception("User not found with phone: " + phone));
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        return userDtoConverter.convertUserDto(user);
    }
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public UserDto updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        try {
            return userDtoConverter.updateUser(user.getId(), userUpdateRequest, userRepository);
        } catch (Exception e) {
            throw new UserUpdateException("Failed to update user with ID " + id);
        }
    }

    public void deactivateUser(Long id) {
        changeActivateUser(id,false);
    }

    public void activateUser(Long id) {
        changeActivateUser(id,true);
    }

    public void changeActivateUser(Long id, Boolean activate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        user.setIsActive(activate);
        userRepository.save(user);
    }


    public ResponseEntity<String> addFriendRequest(Long id, UserAddFriendRequest userFriendRequest) {
        User requester = findUserById(id);
        User receiver = findUserByPhone(userFriendRequest.getPhone());

        if (requester.equals(receiver)) {
            throw new BadRequestexception("You cannot send a friend request to yourself.");
        }
        if(requester.getWaitingFriendList().contains(receiver.getId())){
            throw new BadRequestexception("Your friend request is already waiting...");
        }
        if (requester.getFriendList().contains(receiver.getId())) {
            throw new AlreadyFriendsException("These users are already friends.");
        }
        if(receiver.getWaitingFriendList().contains(requester.getId())){
            throw new AlreadyFriendsException("Friend request has already been sent.");
        }

        receiver.getWaitingFriendList().add(requester.getId());
        userRepository.save(receiver);

        return ResponseEntity.status(HttpStatus.OK).body("Friend request has been sent.");
    }
    public ResponseEntity<String> acceptFriendRequest(Long requesterId, Long receiverId) {
        User requester = findUserById(requesterId);
        User receiver = findUserById(receiverId);

        if(!receiver.getWaitingFriendList().contains(requester.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Friend request not found");
        }

        UserFriend newFriendship = new UserFriend();
        newFriendship.setUserId(requester.getId());
        newFriendship.setFriendId(receiver.getId());
        newFriendship.setIsActive(true);

        receiver.getWaitingFriendList().remove(requester.getId());
        receiver.getFriendList().add(requester.getId());
        requester.getFriendList().add(receiver.getId());

        userRepository.save(receiver);
        userRepository.save(requester);
        userFriendRepository.save(newFriendship);

        return ResponseEntity.status(HttpStatus.OK).body("Friend request accepted.");
    }
    public ResponseEntity<String> rejectFriendRequest(Long requesterId, Long receiverId) {
        User requester = findUserById(requesterId);
        User receiver = findUserById(receiverId);
        if(!receiver.getWaitingFriendList().contains(requester.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Friend request not found");
        }

        receiver.getWaitingFriendList().remove(requester.getId());
        userRepository.save(receiver);

        return ResponseEntity.status(HttpStatus.OK).body("Friend request rejected.");
    }
    public List<UserFriendDto> getAllFriends(Long userId) {
            return userFriendDtoConverter.getFriends(userId);
    }

    public ResponseEntity<String> deactivateFriendship(Long userId, Long friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);

        UserFriend friendship1 = userFriendRepository.findByUserIdAndFriendIdAndIsActiveTrue(userId, friendId);
        UserFriend friendship2 = userFriendRepository.findByUserIdAndFriendIdAndIsActiveTrue(friendId, userId);

        if (friendship1 != null) {
            friendship1.setIsActive(false);
            userFriendRepository.save(friendship1);
        }

        if (friendship2 != null) {
            friendship2.setIsActive(false);
            userFriendRepository.save(friendship2);
        }

        if(user.getFriendList().contains(friend.getId()) && friend.getFriendList().contains(userId)) {
            user.getFriendList().remove(friend.getId());
            userRepository.save(user);
            friend.getFriendList().remove(user.getId());
            userRepository.save(friend);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no friendship.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Friendship has been deactivated.");
    }
}