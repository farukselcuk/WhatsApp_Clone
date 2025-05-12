package tr.com.ind.dto;

import lombok.Data;

@Data
public class GroupUpdateRequest {
    private String name;
    private String status;
    private byte[] photo;
    private Boolean isActive;
}
