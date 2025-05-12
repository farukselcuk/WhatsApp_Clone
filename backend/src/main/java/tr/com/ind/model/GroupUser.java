package tr.com.ind.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groupUser")
public class GroupUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupUser_id;

    @ManyToOne  // Çokça GroupUser 1 tane group'a üye olabilir.
    @JoinColumn(name = "groupId", referencedColumnName = "Group_id")
    private Group group;

    @ManyToOne //Bir kullanıcı birden çok gruba dahil olabilir.
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private GroupRoles role;

}
