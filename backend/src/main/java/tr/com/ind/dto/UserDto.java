package tr.com.ind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String phone;
    private String name;
    private String surname;
    private byte[] photo;
    private Boolean isActive;
    private LocalDateTime createdDate;
    private List<String> groupList;
    private List<String> ownerGroupList;
    private List<Long> friendIdList;
    private List<Long> waitingFriendIdList;


}
