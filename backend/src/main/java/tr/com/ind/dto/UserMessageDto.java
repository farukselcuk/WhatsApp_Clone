package tr.com.ind.dto;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.ind.model.User;

@Getter
@Setter
@NoArgsConstructor
public class UserMessageDto {
    private Long id;
    private String text;
    private byte[] photo;
    private User receiver_id;
    private User sender_id;
    private User message_id;

    public UserMessageDto(Long id, String text,byte[] photo ,User receiver_id, User sender_id, User message_id) {
        this.id = id;
        this.text = text;
        this.photo=photo;
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
        this.message_id = message_id;
    }
}
