package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.roles.RightsRolesRepository;
import repository.user.UserRepository;
import view.dto.AdministratorDTO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static database.Constants.Roles.EMPLOYEE;

public class UserServiceMySQL implements UserService {
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllUsersByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public User findUserById(AdministratorDTO administratorDTO) {
        List<User> user = userRepository.findAll().stream().filter(u -> u.getId().equals(Long.valueOf(administratorDTO.getId()))).collect(Collectors.toList());

        return user.isEmpty() ? null : user.get(0);
    }

    @Override
    public void deleteUserById(AdministratorDTO administratorDTO) {
        userRepository.removeUserById(Long.valueOf(administratorDTO.getId()));
    }

    @Override
    public Notification<Boolean> updateUser(AdministratorDTO administratorDTO) {
        User user = new UserBuilder()
                .setId(Long.valueOf(administratorDTO.getId()))
                .setUsername(administratorDTO.getUsername())
                .setPassword(administratorDTO.getPassword())
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(administratorDTO.getPassword()));
            userRepository.updateById(user.getId(), user);
        }

        return userRegisterNotification;
    }

    @Override
    public Notification<Boolean> register(AdministratorDTO administratorDTO) {
        Role customerRole = rightsRolesRepository.findRoleByTitle(administratorDTO.getRole());
        User user = new UserBuilder()
                .setUsername(administratorDTO.getUsername())
                .setPassword(administratorDTO.getPassword())
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(administratorDTO.getPassword()));
            userRegisterNotification.setResult(userRepository.save(user));
        }

        return userRegisterNotification;
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
