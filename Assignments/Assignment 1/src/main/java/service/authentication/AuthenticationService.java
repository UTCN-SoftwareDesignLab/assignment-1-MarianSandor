package service.authentication;

import model.User;
import model.validation.Notification;
import view.dto.AdministratorDTO;

public interface AuthenticationService {

    Notification<User> login(String username, String password);

    boolean logout(User user);
}
