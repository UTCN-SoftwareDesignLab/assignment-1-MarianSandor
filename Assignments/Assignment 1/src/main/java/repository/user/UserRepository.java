package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    List<User> findAllByRole(String role);

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void updateById(Long userId, User newUser);

    void removeUserById(Long userId);

    void removeAll();

    User findById(Long userId);

}
