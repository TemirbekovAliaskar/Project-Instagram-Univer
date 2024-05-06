package instagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_gen")
    @SequenceGenerator(name = "post_gen", sequenceName = "post_seq", allocationSize = 1)
    private Long id;
    private String title;
    @Column(length = 1500)
    private String description;
    @Column(name = "create_at")
    private LocalDate createAt;

    @ManyToOne(cascade = {DETACH})
    private User user;

    @OneToMany(mappedBy = "post", cascade = {PERSIST, MERGE, REFRESH, REMOVE})
    private List<Image> images = new ArrayList<>();

    @OneToOne(cascade = {REMOVE, PERSIST, MERGE})
    private Like like;

    @OneToMany(mappedBy = "post", cascade = {REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    @PrePersist
    public void preSave(){
        this.createAt = LocalDate.now();
    }
}
