package tr.com.ind.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class GroupMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @JoinColumn(name = "photo")
    private byte[] photo;


    @ManyToOne
    @JoinColumn(name = "group_id")
    private User group_id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private User message_id;

    public GroupMessage(Long id, String text,byte[] photo,User group_id, User message_id) {
        this.id = id;
        this.text = text;
        this.photo=photo;
        this.group_id = group_id;
        this.message_id = message_id;
    }

    public GroupMessage() {

    }
}
