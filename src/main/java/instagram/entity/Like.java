package instagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_gen")
    @SequenceGenerator(name = "like_gen", sequenceName = "like_seq", allocationSize = 1)
    private Long id;
    @Column(name = "is_like")

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> isLikes = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> comLikes = new ArrayList<>();

}
