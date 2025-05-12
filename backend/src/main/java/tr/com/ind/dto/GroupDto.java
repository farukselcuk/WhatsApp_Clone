package tr.com.ind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private Long group_id;
    private String group_name;
    private byte[] group_photo;
    private String status;
    private String owner_name;
    private Boolean isActive;
    private LocalDateTime createdTime;
    private List<String> members;

}
