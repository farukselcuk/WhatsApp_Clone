package tr.com.ind.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @JoinColumn(name = "photo")
    private byte[] photo;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver_id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender_id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private User message_id;

    public UserMessage(Long id, String text,byte[] photo ,User receiver_id, User sender_id, User message_id) {
        this.id = id;
        this.text = text;
        this.photo=photo;
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
        this.message_id = message_id;
    }

    public UserMessage() {

    }

}
