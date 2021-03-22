package repository.user;

import model.Client;
import model.Type;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.roles.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static database.Constants.Tables.*;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String fetchUsersSql = "select * from " + USER;
            ResultSet userResultSet = statement.executeQuery(fetchUsersSql);
            while (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();

                allUsers.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allUsers;
    }

    @Override
    public List<User> findAllByRole(String role) {
        List<User> allUsers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String fetchUsersSql = "select u.id as id, u.username as username, u.password as password " +
                                    "from " + USER + " u"+
                                    " join user_role ur on `u`.id = `ur`.user_id" +
                                    " join role r on `ur`.role_id = `r`.id" +
                                    " where `r`.role=\'" + role + "\';";
            ResultSet userResultSet = statement.executeQuery(fetchUsersSql);
            while (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();

                allUsers.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allUsers;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateById(Long userId, User newUser) {
        try {
            PreparedStatement updateUserStatement = connection
                    .prepareStatement("UPDATE user SET `username`=?, `password`=? WHERE `id`='" + userId + "'");
            updateUserStatement.setString(1, newUser.getUsername());
            updateUserStatement.setString(2, newUser.getPassword());

            updateUserStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeUserById(Long userId) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from " + USER + " where `id`='" + userId + "'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from " + USER + " where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(Long userId) {
        return findAll().stream().filter(u -> u.getId().equals(userId)).collect(Collectors.toList()).get(0);
    }
}
