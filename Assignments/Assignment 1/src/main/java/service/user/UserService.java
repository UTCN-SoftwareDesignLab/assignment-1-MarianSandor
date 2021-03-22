package service.user;

import model.User;
import model.validation.Notification;
import view.dto.AdministratorDTO;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    List<User> findAllUsersByRole(String role);

    User findUserById(AdministratorDTO administratorDTO);

    Notification<Boolean> register(AdministratorDTO administratorDTO);

    void deleteUserById(AdministratorDTO administratorDTO);

    Notification<Boolean> updateUser(AdministratorDTO administratorDTO);

}
