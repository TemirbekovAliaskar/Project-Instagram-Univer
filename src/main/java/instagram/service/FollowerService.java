package instagram.service;

public interface FollowerService {
    int getNumberOfSubscribers(Long userId);
    int getNumberOfSubscriptions(Long userId);
    void addSubscriber(Long userId, Long subscriberId);
}
