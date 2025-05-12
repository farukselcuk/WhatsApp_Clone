package tr.com.ind.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String name;
    private String surname;
    private String password;
    private String status;
    private byte[] photo;
    private Boolean isActive;
}