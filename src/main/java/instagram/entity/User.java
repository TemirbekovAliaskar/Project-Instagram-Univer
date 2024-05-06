package instagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    @Column(name = "user_name", unique = true)
    private String userName;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = {REMOVE, PERSIST, MERGE, REFRESH})
    private UserInfo userInfo;

    @OneToOne(cascade = {REMOVE, PERSIST, MERGE})
    private Follower follower;

    @OneToMany(mappedBy = "user", cascade = {REMOVE}, fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {REMOVE})
    private List<Comment> comments = new ArrayList<>();
}
