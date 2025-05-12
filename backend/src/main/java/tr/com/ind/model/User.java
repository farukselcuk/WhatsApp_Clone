package tr.com.ind.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
    private String surname;
    private String status;
    private byte[] photo;
    private Boolean isActive;
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Group> ownerGroupList;

    @ManyToMany
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groupList;


    @ElementCollection
    private List<Long> friendList = new ArrayList<>();
    @ElementCollection
    private List<Long> waitingFriendList = new ArrayList<>();



    public User(Long id, String phone, String password, String name, String surname, String status,
                byte[] photo,
                Boolean isActive,
                LocalDateTime createdDate,
                Role role,
                List<Group> ownerGroupList,
                List<Group> groupList,
                List<Long> friendList,
                List<Long> waitingFriendList
    ) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.status = status;
        this.photo = photo;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.role = role;
        this.ownerGroupList = ownerGroupList != null ? ownerGroupList : new ArrayList<>();
        this.groupList = groupList != null ? groupList : new ArrayList<>();
        this.friendList = friendList != null ? friendList : new ArrayList<>();
        this.waitingFriendList = waitingFriendList != null ? waitingFriendList : new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.phone;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
