package tr.com.ind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserFriendDto {
    private String name;
    private String surname;
    private String phone;
    private byte[] photo;
    private String status;
}
