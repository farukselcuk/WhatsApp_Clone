package tr.com.ind.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.ind.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GroupMessageDto {
    private Long id;
    private String text;
    private byte[] photo;
    private LocalDateTime createTime = LocalDateTime.now();
    private User group_id;
    private User message_id;

    public GroupMessageDto(Long id, String text, byte[] photo,LocalDateTime createTime,User group_id, User message_id) {
        this.id = id;
        this.text = text;
        this.photo=photo;
        this.createTime=createTime;
        this.group_id = group_id;
        this.message_id = message_id;
    }
}
