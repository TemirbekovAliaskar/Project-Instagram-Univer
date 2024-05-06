package instagram.service;

import instagram.entity.User;
import instagram.exception.MyException;

import java.util.List;

public interface UserService {
    void signUp(User newUser);
    User findUserByNameAndPassword(User user) throws MyException;
    List<User> findUserByUserName(Long userId, String keyword);
    void updateUser(Long id, User user);
    User findById(Long userId);
    void deleteUserByPass(String password, Long userId) throws MyException;
    List<User> subscriptionsOfUser(Long userId);
    List<User> subscribersOfUser(Long userId);
    User findOtherUserById(Long userId, Long subId) throws MyException;
}
