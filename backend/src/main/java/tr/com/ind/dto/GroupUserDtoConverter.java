package tr.com.ind.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.ind.model.Group;
import tr.com.ind.model.GroupUser;
import tr.com.ind.repository.GroupUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupUserDtoConverter {
    private final GroupUserRepository groupUserRepository;

    public GroupUserDto toDto(GroupUser from) {
        return new GroupUserDto(
                from.getGroupUser_id(),
                from.getGroup().getGroup_id(),
                from.getGroup().getGroup_name(),
                from.getUser().getId(),
                from.getUser().getName(),
                from.getUser().getSurname(),
                from.getRole()
        );
    }
    public List<GroupUserDto> toDto(List<GroupUser> fromList) {
        List<GroupUserDto> list = fromList.stream().map(from -> new GroupUserDto(
                from.getGroupUser_id(),
                from.getGroup().getGroup_id(),
                from.getGroup().getGroup_name(),
                from.getUser().getId(),
                from.getUser().getName(),
                from.getUser().getSurname(),
                from.getRole()
        )).collect(Collectors.toList());
        return list;

    }

}
