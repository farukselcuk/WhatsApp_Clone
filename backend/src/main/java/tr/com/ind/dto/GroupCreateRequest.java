package tr.com.ind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateRequest {
    private String name;
    private byte[] photo;
    private String status;
    private Long owner_id;   // postmande çağırırken mutlaka "owner_id": "1,2,3..."  olarak çağırmalıyız.
}
