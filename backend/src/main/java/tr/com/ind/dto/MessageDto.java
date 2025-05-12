package tr.com.ind.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private Long id;
    private String text;
    private boolean isActive;
    private LocalDateTime createTime = LocalDateTime.now();
    private Long createdById;
    private String status;
    private String message;
    public MessageDto(Long id, String text, boolean isActive, LocalDateTime createTime, Long createdById, String status, String message) {
        this.id = id;
        this.text = text;
        this.isActive = isActive;
        this.createTime = createTime;
        this.createdById = createdById;
        this.status = status;
        this.message = message;
    }
    public static MessageDto noContent() {
        MessageDto dto = new MessageDto();
        dto.setStatus("200 OK");             // Rest Api Videosu !
        dto.setMessage("Message successfully deleted.");
        return dto;
    }
    public static MessageDto notFound() {
        MessageDto dto = new MessageDto();
        dto.setStatus("404 NOT FOUND");
        dto.setMessage("Message not found.");
        return dto;
    }

}
