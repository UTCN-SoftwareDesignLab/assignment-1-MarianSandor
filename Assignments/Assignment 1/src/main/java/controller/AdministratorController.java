package controller;

import database.Constants;
import model.validation.Notification;
import service.log.LogService;
import service.user.UserService;
import view.LoginView;
import view.administartor.AdministratorPanels;
import view.administartor.AdministratorView;
import view.employee.EmployeePanels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorController {

    private final AdministratorView administratorView;
    private final LoginView loginView;
    private final UserService userService;
    private final LogService logService;

    public AdministratorController(AdministratorView administratorView, LoginView loginView, UserService userService, LogService logService) {
        this.administratorView = administratorView;
        this.loginView = loginView;
        this.userService = userService;
        this.logService = logService;

        administratorView.setViewButtonListener(new AdministratorController.ViewButtonListener());
        administratorView.setAddButtonListener(new AdministratorController.AddButtonListener());
        administratorView.setDeleteButtonListener(new AdministratorController.DeleteButtonListener());
        administratorView.setUpdateButtonListener(new AdministratorController.UpdateButtonListener());
        administratorView.setSearchButtonListener(new AdministratorController.SearchButtonListener());
        administratorView.setConfirmAddButtonListener(new AdministratorController.ConfirmAddButtonListener());
        administratorView.setConfirmDeleteButtonListener(new AdministratorController.ConfirmDeleteButtonListener());
        administratorView.setHomeButtonListener(new AdministratorController.HomeButtonListener());
        administratorView.setLogoutButtonListener(new AdministratorController.LogoutButtonListener());
        administratorView.setReportButtonListener(new AdministratorController.ReportButtonListener());
        administratorView.setConfirmReportBtn(new AdministratorController.ConfirmReportButtonListener());

        initialize();
    }

    public void initialize() {
        administratorView.setUsers(userService.findAllUsersByRole(Constants.Roles.EMPLOYEE));
        administratorView.setPanel(AdministratorPanels.MAIN);
    }

    public class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorView.setPanel(AdministratorPanels.SEARCH);
            administratorView.setVisible();
        }
    }

    public class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorView.setPanel(AdministratorPanels.ADD);
            administratorView.setVisible();
        }
    }

    public class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorView.setPanel(AdministratorPanels.DELETE);
            administratorView.setVisible();
        }
    }

    public class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> updateNotification = userService.updateUser(administratorView.getAdministratorDTO());

            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), updateNotification.getFormattedErrors());
            }
            else {
                administratorView.setUsers(userService.findAllUsersByRole(Constants.Roles.EMPLOYEE));
                administratorView.setPanel(AdministratorPanels.MAIN);
                administratorView.setVisible();
            }
        }
    }

    public class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorView.setSearchedUser(userService.findUserById(administratorView.getAdministratorDTO()));

            administratorView.setPanel(AdministratorPanels.VIEW);
            administratorView.setVisible();
        }
    }

    public class ConfirmAddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> registerNotification = userService.register(administratorView.getAdministratorDTO());

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                administratorView.setUsers(userService.findAllUsersByRole(Constants.Roles.EMPLOYEE));
                administratorView.setPanel(AdministratorPanels.MAIN);
                administratorView.setVisible();
            }
        }
    }

    public class ConfirmDeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            userService.deleteUserById(administratorView.getAdministratorDTO());

            administratorView.setUsers(userService.findAllUsersByRole(Constants.Roles.EMPLOYEE));
            administratorView.setPanel(AdministratorPanels.MAIN);
            administratorView.setVisible();
        }
    }

    public class HomeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorView.setPanel(AdministratorPanels.MAIN);
            administratorView.setVisible();
        }
    }

    public class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            administratorView.setNotVisible();
            loginView.setVisible();
        }
    }

    public class ReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            administratorView.setPanel(AdministratorPanels.REPORT_SEARCH);
            administratorView.setVisible();
        }
    }

    public class ConfirmReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorView.setLogs(logService.findLogsByUserAndDate(administratorView.getAdministratorDTO()));

            administratorView.setPanel(AdministratorPanels.REPORT);
            administratorView.setVisible();
        }
    }
}
