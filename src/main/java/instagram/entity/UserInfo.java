package instagram.entity;

import instagram.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_infos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_gen")
    @SequenceGenerator(name = "user_info_gen", sequenceName = "user_info_seq", allocationSize = 1)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(length = 500)
    private String biography;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(length = 2000)
    private String image;

}
