package tr.com.ind.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tr.com.ind.model.GroupRoles;

@Data
@AllArgsConstructor
public class GroupUserDto {
    private Long id;
    private Long groupId;
    private String groupName;

    private Long userId;
    private String name;
    private String surname;

    private GroupRoles role;
}
