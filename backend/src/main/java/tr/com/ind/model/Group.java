package tr.com.ind.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.cfg.defs.EmailDef;

import java.time.LocalDateTime;
import java.util.*;


@NoArgsConstructor
@Entity
@Data
@Getter
@Setter
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_id;

    @Column(nullable = false)
    private String group_name;
    private String group_status;
    private byte[] group_photo;
    private Boolean isActive;
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

//    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<GroupUser> groupUsers;


    @ManyToMany(mappedBy = "groupList" ,fetch = FetchType.LAZY)
    private List<User> membersList;


    public Group(Long group_id,
                 String group_name,
                 String group_status,
                 byte[] group_photo,
                 Boolean isActive,
                 LocalDateTime createdTime,
                 User owner,
                 List<User> membersList) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.group_status = group_status;
        this.group_photo = group_photo;
        this.isActive = isActive;
        this.createdTime = createdTime;
        this.owner = owner;
        this.membersList = membersList != null ? membersList : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(group_id, group.group_id) && Objects.equals(group_name, group.group_name) && Objects.equals(group_status, group.group_status) && Objects.deepEquals(group_photo, group.group_photo) && Objects.equals(isActive, group.isActive) && Objects.equals(createdTime, group.createdTime) && Objects.equals(owner, group.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group_id, group_name, group_status, Arrays.hashCode(group_photo), isActive, createdTime, owner);
    }
}
