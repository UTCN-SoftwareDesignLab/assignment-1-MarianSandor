package controller;

import model.builder.LogBuilder;
import model.validation.Notification;
import service.bill.BillService;
import service.client.ClientService;
import service.log.LogService;
import view.LoginView;
import view.employee.EmployeePanels;
import view.employee.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import static database.Constants.Rights.*;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final LoginView loginView;
    private final ClientService clientService;
    private final BillService billService;
    private final LogService logService;

    public EmployeeController(EmployeeView employeeView, LoginView loginView, ClientService clientService, BillService billService, LogService logService) {
        this.employeeView = employeeView;
        this.loginView = loginView;
        this.clientService = clientService;
        this.billService = billService;
        this.logService = logService;

        employeeView.setViewButtonListener(new EmployeeController.ViewButtonListener());
        employeeView.setAddButtonListener(new EmployeeController.AddButtonListener());
        employeeView.setDeleteButtonListener(new EmployeeController.DeleteButtonListener());
        employeeView.setUpdateButtonListener(new EmployeeController.UpdateButtonListener());
        employeeView.setTransferButtonListener(new EmployeeController.TransferButtonListener());
        employeeView.setProcessButtonListener(new EmployeeController.ProcessButtonListener());
        employeeView.setSearchButtonListener(new EmployeeController.SearchButtonListener());
        employeeView.setConfirmAddButtonListener(new EmployeeController.ConfirmAddButtonListener());
        employeeView.setConfirmDeleteButtonListener(new EmployeeController.ConfirmDeleteButtonListener());
        employeeView.setConfirmTransferButtonListener(new EmployeeController.ConfirmTransferButtonListener());
        employeeView.setHomeButtonListener(new EmployeeController.HomeButtonListener());
        employeeView.setConfirmProcessButtonListener(new EmployeeController.ConfirmProcessButtonListener());
        employeeView.setLogoutButtonListener(new EmployeeController.LogoutButtonListener());

        initialize();
    }

    public void initialize() {
        employeeView.setClients(clientService.findAll());
        employeeView.setPanel(EmployeePanels.MAIN);
    }

    public class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setPanel(EmployeePanels.SEARCH);
            employeeView.setVisible();
        }
    }

    public class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setPanel(EmployeePanels.ADD);
            employeeView.setVisible();
        }
    }

    public class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setPanel(EmployeePanels.DELETE);
            employeeView.setVisible();
        }
    }

    public class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> updateNotification = clientService.updateClient(employeeView.getEmployeeDTO(), employeeView.getSearchedClient(), employeeView.getSearchedClientInfo());

            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), updateNotification.getFormattedErrors());
            }
            else {
                logService.log(new LogBuilder()
                        .setUser(LoggedUser.getLoggedUser().getUsername())
                        .setAction(UPDATE_CLIENT + " client_id=" + employeeView.getEmployeeDTO().getId())
                        .setDate(LocalDate.now())
                        .build()
                );

                employeeView.setClients(clientService.findAll());
                employeeView.setPanel(EmployeePanels.MAIN);
                employeeView.setVisible();
            }
        }
    }

    public class TransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setPanel(EmployeePanels.TRANSFER);
            employeeView.setVisible();
        }
    }

    public class ProcessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setPanel(EmployeePanels.PROCESS);
            employeeView.setVisible();
        }
    }

    public class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setSearchedClient(clientService.getClient(employeeView.getEmployeeDTO()));
            employeeView.setSearchedClientInfo(clientService.getClientInfo(employeeView.getEmployeeDTO()));

            logService.log(new LogBuilder()
                    .setUser(LoggedUser.getLoggedUser().getUsername())
                    .setAction(VIEW_CLIENT + " client_id=" + employeeView.getEmployeeDTO().getId())
                    .setDate(LocalDate.now())
                    .build()
            );

            employeeView.setPanel(EmployeePanels.VIEW);
            employeeView.setVisible();
        }
    }

    public class ConfirmAddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> createNotification = clientService.saveClient(employeeView.getEmployeeDTO());

            if (createNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), createNotification.getFormattedErrors());
            }
            else {
                logService.log(new LogBuilder()
                        .setUser(LoggedUser.getLoggedUser().getUsername())
                        .setAction(CREATE_CLIENT + " client_id=" + employeeView.getEmployeeDTO().getId())
                        .setDate(LocalDate.now())
                        .build()
                );

                employeeView.setClients(clientService.findAll());
                employeeView.setPanel(EmployeePanels.MAIN);
                employeeView.setVisible();
            }
        }
    }

    public class ConfirmDeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientService.deleteClient(employeeView.getEmployeeDTO());

            logService.log(new LogBuilder()
                    .setUser(LoggedUser.getLoggedUser().getUsername())
                    .setAction(DELETE_CLIENT + " client_id=" + employeeView.getEmployeeDTO().getId())
                    .setDate(LocalDate.now())
                    .build()
            );

            employeeView.setClients(clientService.findAll());
            employeeView.setPanel(EmployeePanels.MAIN);
            employeeView.setVisible();
        }
    }

    public class ConfirmTransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientService.transferMoney(employeeView.getEmployeeDTO());

            logService.log(new LogBuilder()
                    .setUser(LoggedUser.getLoggedUser().getUsername())
                    .setAction(TRANSFER_MONEY + " from client_id=" + employeeView.getEmployeeDTO().getIdFrom() +
                            " to client_id" + employeeView.getEmployeeDTO().getIdFrom() +
                            " amount: " + employeeView.getEmployeeDTO().getBalance())
                    .setDate(LocalDate.now())
                    .build()
            );

            employeeView.setClients(clientService.findAll());
            employeeView.setPanel(EmployeePanels.MAIN);
            employeeView.setVisible();
        }
    }

    public class HomeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setPanel(EmployeePanels.MAIN);
            employeeView.setVisible();
        }
    }

    public class ConfirmProcessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            billService.addBill(employeeView.getEmployeeDTO());
            clientService.payBill(employeeView.getEmployeeDTO());

            logService.log(new LogBuilder()
                    .setUser(LoggedUser.getLoggedUser().getUsername())
                    .setAction(PROCESS_BILL + " client_id=" + employeeView.getEmployeeDTO().getId() +
                                " total: " + employeeView.getEmployeeDTO().getBalance())
                    .setDate(LocalDate.now())
                    .build()
            );

            employeeView.setClients(clientService.findAll());
            employeeView.setPanel(EmployeePanels.MAIN);
            employeeView.setVisible();
        }
    }

    public class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            employeeView.setNotVisible();
            loginView.setVisible();
        }
    }
}
