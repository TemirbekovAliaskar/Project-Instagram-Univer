package instagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "followers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follower{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "follower_gen")
    @SequenceGenerator(name = "follower_gen", sequenceName = "follower_seq", allocationSize = 1)
    private Long id;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> subscribers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> subscriptions = new ArrayList<>();


}
