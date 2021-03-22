package controller;

import model.User;
import model.validation.Notification;
import service.authentication.AuthenticationService;
import view.LoginView;
import view.administartor.AdministratorView;
import view.employee.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class LoginController {
    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final AdministratorView administratorView;
    private final AuthenticationService authenticationService;

    public LoginController(LoginView loginView, EmployeeView employeeView, AdministratorView administratorView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.employeeView = employeeView;
        this.administratorView = administratorView;
        this.authenticationService = authenticationService;
        loginView.setLoginButtonListener(new LoginButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");

                loginView.clearFields();
                loginView.setNotVisible();

                LoggedUser.logUser(loginNotification.getResult());

                String role = loginNotification.getResult().getRoles().get(0).getRole();
                switch(role) {
                    case EMPLOYEE:
                        employeeView.setVisible();
                        break;
                    case ADMINISTRATOR:
                        administratorView.setVisible();
                        break;
                    default:
                        loginView.setVisible();
                }
            }
        }
    }
}