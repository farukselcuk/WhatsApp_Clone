package tr.com.ind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.ind.dto.*;
import tr.com.ind.exception.UserNotFoundException;
import tr.com.ind.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenResponse> login(@RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserDto response = userService.createUser(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login/{id}/addFriendRequest")
    public ResponseEntity<String> addFriendRequest(@PathVariable Long id, @RequestBody UserAddFriendRequest userFriendRequest) {
        return userService.addFriendRequest(id, userFriendRequest);
    }

    @PostMapping("/login/{receiver}/acceptFriend/{requester}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long receiver, @PathVariable Long requester) {
        return userService.acceptFriendRequest(requester, receiver);
    }

    @PostMapping("/login/{receiver}/rejectFriend/{requester}")
    public ResponseEntity<String> rejectFriendRequest(@PathVariable Long receiver, @PathVariable Long requester) {
        return userService.rejectFriendRequest(requester, receiver);
    }

    @GetMapping("/login/{id}/getAllFriends")
    public ResponseEntity<List<UserFriendDto>> getAllFriends(@PathVariable Long id) {
        List<UserFriendDto> friends = userService.getAllFriends(id);
        if (friends.isEmpty()) {
            throw new UserNotFoundException("Friends not found");
        }
        return ResponseEntity.ok(friends);
    }

    @PostMapping("/login/{userId}/deactivateFriendship/{friendId}")
    public ResponseEntity<String> deactivateFriendship(@PathVariable Long userId, @PathVariable Long friendId) {
        return userService.deactivateFriendship(userId,friendId);
    }


    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Users not found");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/login/phone/{phone}")
    public ResponseEntity<UserDto> getUserByPhone(@PathVariable String phone) {
        try {
            return ResponseEntity.ok(userService.getUserByPhone(phone));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/login/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    @PutMapping("/login/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.updateUser( id ,userUpdateRequest));
    }


    @PatchMapping("/login/{id}/deactive")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/login/{id}/active")
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        userService.activateUser(id);
        return ResponseEntity.ok().build();
    }

}
