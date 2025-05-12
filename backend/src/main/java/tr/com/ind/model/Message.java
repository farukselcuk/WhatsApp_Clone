package tr.com.ind.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private byte[] photo;
    private boolean isActive;
    private LocalDateTime createTime = LocalDateTime.now();

    @ManyToOne
    private User createdBy;

    public Message(Long id, String text, byte[] photo, boolean isActive, LocalDateTime createTime, User createdTime) {
        this.id = id;
        this.text = text;
        this.photo = photo;
        this.isActive = isActive;
        this.createTime = createTime;
        this.createdBy = createdBy;
    }

    public Message(String text, byte[] photo, boolean isActive, LocalDateTime createTime, User createdTime) {
        this.text = text;
        this.photo = photo;
        this.isActive = isActive;
        this.createTime = createTime;
        this.createdBy = createdBy;
    }

    public Message() {

    }

}
