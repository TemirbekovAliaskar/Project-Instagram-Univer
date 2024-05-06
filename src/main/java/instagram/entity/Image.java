package instagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_gen")
    @SequenceGenerator(name = "image_gen", sequenceName = "image_seq", allocationSize = 1)
    private Long id;
    @Column(name = "image_url", length = 2000)
    private String imageURL;

    @OneToMany(cascade = {DETACH})
    private List<User> users = new ArrayList<>();

    @ManyToOne
    private Post post;
}
