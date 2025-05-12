package tr.com.ind.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import tr.com.ind.model.Group;
import tr.com.ind.model.Role;
import tr.com.ind.model.User;
import tr.com.ind.repository.UserRepository;
import tr.com.ind.service.JwtService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserDto convertUserDto(User from) {
        return new UserDto(from.getId(),
                from.getPhone(),
                from.getName(),
                from.getSurname(),
                from.getPhoto(),
                from.getIsActive(),
                LocalDateTime.now(),
                GroupGetName(from.getGroupList()),
                GroupGetName(from.getOwnerGroupList()),
                from.getFriendList(),
                from.getWaitingFriendList()
        );
    }

    public List<UserDto> convertUserDto(List<User> fromList){
        return fromList.stream()
                .map(this::convertUserDto)
                .collect(Collectors.toList());
    }


    public List<String> GroupGetName(List<Group> groups) {
        return groups.stream()
                .map(Group::getGroup_name)
                .collect(Collectors.toList());
    }


    public UserTokenResponse createUserToken(UserRegisterRequest request){
        User user = new User(null,
                request.getPhone(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getSurname(),
                null,
                request.getPhoto(),
                true,
                LocalDateTime.now(),
                Role.USER,
                null,
                null,
                null,
                null
                );
        var jwtToken = jwtService.generateToken(user);
        return UserTokenResponse.builder()
                .token(jwtToken)
                .build();

    }


    public UserDto createUser(UserRegisterRequest request){
        User user = new User(null,
                request.getPhone(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getSurname(),
                null,
                request.getPhoto(),
                true,
                LocalDateTime.now(),
                Role.USER,
                null,
                null
                ,null,
                null
        );
        return convertUserDto(userRepository.save(user));
    }


    public UserDto updateUser(Long id, UserUpdateRequest userUpdateRequest, UserRepository userRepository) {
        User user = findUserById(id, userRepository);
        if (!user.getIsActive()) {
            throw new RuntimeException("User is not active");
        }
        user.setPassword(userUpdateRequest.getPassword());
        user.setName(userUpdateRequest.getName());
        user.setSurname(userUpdateRequest.getSurname());
        user.setStatus(userUpdateRequest.getStatus());
        user.setPhoto(userUpdateRequest.getPhoto());

        return convertUserDto(userRepository.save(user));
    }

    private User findUserById(Long id, UserRepository userRepository) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
