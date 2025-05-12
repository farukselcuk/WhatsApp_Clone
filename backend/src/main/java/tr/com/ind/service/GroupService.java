package tr.com.ind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.ind.dto.*;
import tr.com.ind.exception.*;
import tr.com.ind.model.Group;
import tr.com.ind.model.GroupRoles;
import tr.com.ind.model.GroupUser;
import tr.com.ind.model.User;
import tr.com.ind.repository.GroupRepository;
import tr.com.ind.repository.GroupUserRepository;
import tr.com.ind.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupDtoConverter groupDtoConverter;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final GroupUserRepository groupUserRepository;

    public GroupDto createGroup(GroupCreateRequest request) {
        try {
            return groupDtoConverter.createGroup(request);
        }catch (Exception e){
            throw new GroupCreationException("Failed to create group");
        }

    }

    public List<GroupDto> getAllgroups() {
        return groupDtoConverter.toDto(groupRepository.findAll());
    }

    public GroupDto updateGroup(Long id, GroupUpdateRequest request) {
        Group group = groupRepository.findById(id).
                orElseThrow(() -> new GroupNotFoundException("Group with GroupID " + id + " not found"));
        try{
            return groupDtoConverter.updateGroup(id,request,groupRepository);
        }catch (Exception e){
            throw new GroupUpdateException("Failed to update group with GroupID " + id);
        }

    }

    public void deactivateGroup(Long groupid) {
        changeActivateGroup(groupid,false);
    }
    public void activateGroup(Long id) {
        changeActivateGroup(id,true);
    }
    private void changeActivateGroup(Long groupid, Boolean isActive) {
        Group group = groupRepository.findById(groupid)
                .orElseThrow(() -> new GroupNotFoundException("Group not found with id: " + groupid));
        group.setIsActive(isActive);
        groupRepository.save(group);
    }


    public GroupDto getGroupById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(
                () -> new GroupNotFoundException("Group with GroupID " + id + " not found")
        );
        return groupDtoConverter.toDto(group);
    }


    public List<String> getGroupByUserId(Long id) {
        return getGroupByGroupName(id);

    }
    public List<String> getGroupByGroupName(Long id) {
        User user = userService.findUserById(id);
        List<String> groupNames = new ArrayList<>();

        groupNames.addAll(user.getOwnerGroupList().stream()
                .map(Group::getGroup_name)
                .collect(Collectors.toList()));

        groupNames.addAll(user.getGroupList().stream()
                .map(Group::getGroup_name)
                .collect(Collectors.toList()));

        return groupNames;
    }


    
    public void addMemberToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group with ID " + groupId + " not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        if (user.getGroupList().contains(group)) {
            throw new UserAlreadyMemberException("User with ID " + userId + " is already a member of the group with ID " + groupId);
        }

        GroupUser groupUser = new GroupUser();
        groupUser.setGroup(group);
        groupUser.setUser(user);
        groupUser.setRole(GroupRoles.MEMBER);
        groupUserRepository.save(groupUser);

        user.getGroupList().add(group);

        groupUserRepository.save(groupUser);
    }


}
