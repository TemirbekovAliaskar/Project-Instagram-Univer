package instagram.repository;

import instagram.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    @Query("select u.follower from User u where u.id =:userId")
    Follower findByUserId(Long userId);
 }
