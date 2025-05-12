package tr.com.ind.dto;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.ind.model.Group;
import tr.com.ind.model.GroupUser;
import tr.com.ind.model.GroupRoles;
import tr.com.ind.model.User;
import tr.com.ind.repository.GroupRepository;
import tr.com.ind.repository.GroupUserRepository;
import tr.com.ind.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupDtoConverter {

    private final GroupRepository groupRepository;
    private final UserService userService;
    private final GroupUserRepository groupUserRepository;

    public GroupDto toDto(Group from) {
        return new GroupDto(
                from.getGroup_id(),
                from.getGroup_name(),
                from.getGroup_photo(),
                from.getGroup_status(),
                from.getOwner().getName(),
                from.getIsActive(),
                from.getCreatedTime(),
                getMemberNames(from.getMembersList())
        );
    }
    public List<GroupDto> toDto(List<Group> fromList) {
        List<GroupDto> list = fromList.stream().map(from -> new GroupDto(
                from.getGroup_id(),
                from.getGroup_name(),
                from.getGroup_photo(),
                from.getGroup_status(),
                from.getOwner().getName(),
                from.getIsActive(),
                from.getCreatedTime(),
                getMemberNames(from.getMembersList())
        )).collect(Collectors.toList());
        return list;

    }

    public List<String> getMemberNames(List<User> members) {
        return members.stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }


    public GroupDto createGroup(GroupCreateRequest request) {
        User owner = userService.findUserById(request.getOwner_id());
        LocalDateTime time = LocalDateTime.now();
        Group group = new Group(null,
                request.getName(),
                request.getStatus(),
                request.getPhoto(),
                true,
                time,
                owner,
                null
                );

        owner.getOwnerGroupList().add(group);  // owner olduğu grupları gösteriyor.

        // Grup oluştuktan sonra groupUser'a ownerı eklemem lazım
        GroupUser groupUser = new GroupUser();
        groupUser.setGroup(group);
        groupUser.setUser(owner);
        groupUser.setRole(GroupRoles.OWNER);
        groupUserRepository.save(groupUser);

        return toDto(groupRepository.save(group));
    }




    public GroupDto updateGroup(final Long id,final GroupUpdateRequest updateRequest,final GroupRepository groupRepository) {
        Group group = findGroupById(id);

        if (!group.getIsActive()) {
            throw new RuntimeException("Group is not active");
        }
        group.setGroup_name(updateRequest.getName());
        group.setGroup_photo(updateRequest.getPhoto());
        group.setGroup_status(updateRequest.getStatus());

        return toDto(groupRepository.save(group));
    }

    private Group findGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
    }

}
