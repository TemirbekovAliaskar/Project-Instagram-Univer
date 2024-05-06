package instagram.service.serviceImpl;

import instagram.entity.Follower;
import instagram.entity.User;
import instagram.repository.FollowerRepository;
import instagram.repository.UserRepository;
import instagram.service.FollowerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    @Override
    public int getNumberOfSubscribers(Long userId) {
        Follower follower = followerRepository.findByUserId(userId);
        return follower.getSubscribers().isEmpty() ? 0 : follower.getSubscribers().size();
    }
    @Override
    public int getNumberOfSubscriptions(Long userId) {
        Follower follower = followerRepository.findByUserId(userId);
        return follower.getSubscriptions().isEmpty() ? 0 : follower.getSubscriptions().size();
    }
    @Transactional
    @Override
    public void addSubscriber(Long currentUserId, Long subscriberId) {
        User currentUser = userRepository.findById(currentUserId).orElseThrow();
        User subscriberUser = userRepository.findById(subscriberId).orElseThrow();

        Follower currentUserFollower = currentUser.getFollower();
        Follower subscriberFollower = subscriberUser.getFollower();

        List<Long> currentUserSubscriptions = currentUserFollower.getSubscriptions();
        List<Long> subscriberSubscribers = subscriberFollower.getSubscribers();

        if (currentUserSubscriptions.contains(subscriberId)) {
            currentUserSubscriptions.remove(subscriberId);
            subscriberSubscribers.remove(currentUserId);
        } else {
            currentUserSubscriptions.add(subscriberId);
            subscriberSubscribers.add(currentUserId);
        }
    }
}
