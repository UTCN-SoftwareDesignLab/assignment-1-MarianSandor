package view.administartor;

import database.Constants;
import model.Log;
import model.Role;
import model.User;
import view.dto.AdministratorDTO;
import view.dto.AdministratorDTOBuilder;
import view.employee.EmployeePanels;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdministratorView extends JFrame {
    private JPanel mainPanel;
    private JButton viewBtn;
    private JButton addBtn;
    private JButton deleteBtn;
    private JButton updateBtn;
    private JButton reportSearchBtn;
    private JButton searchBtn;
    private JButton confirmAddBtn;
    private JButton confirmDeleteBtn;
    private JButton confirmReportBtn;
    private JButton logoutBtn;
    private JButton homeBtn;
    private JTextField tfId;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfDateFrom;
    private JTextField tfDateTo;
    private JComboBox<String> cbRole;
    private List<User> users;
    private User searchedUser;
    private List<Log> logs;

    public AdministratorView() throws HeadlessException {
        initializeFields();
    }

    private void initializeFields() {
        mainPanel = new JPanel();
        users = new ArrayList<>();
        viewBtn = new JButton("View Info");
        addBtn = new JButton("Add User");
        deleteBtn = new JButton("Delete User");
        updateBtn = new JButton("Update");
        reportSearchBtn = new JButton("Generate Report");
        searchBtn = new JButton("Search");
        confirmAddBtn = new JButton("Confirm");
        confirmDeleteBtn = new JButton("Confirm");
        confirmReportBtn = new JButton("Confirm");
        homeBtn = new JButton("Home");
        logoutBtn = new JButton("Logout");
        tfId = new JTextField("");
        tfUsername = new JTextField("");
        tfPassword = new JTextField("");
        tfDateFrom = new JTextField("yyyy-mm-dd");
        tfDateTo = new JTextField("yyyy-mm-dd");
        cbRole = new JComboBox<>(Constants.Roles.ROLES);
    }

    private void clearTf() {
        tfId.setText("");
        tfUsername.setText("");
        tfPassword.setText("");
        tfDateFrom.setText("yyyy-mm-dd");
        tfDateTo.setText("yyyy-mm-dd");
    }

    public void setPanel(AdministratorPanels panel) {
        int width = 0;
        int height = 0;
        String title = null;

        if (panel == AdministratorPanels.MAIN) {
            this.setMainPanel();
            width = 500;
            height = 500;
            title = "Administrator - Home";
        }else if (panel == AdministratorPanels.SEARCH) {
            this.setSearchPanel();
            width = 400;
            height = 125;
            title = "Administrator - Search";
        }
        else if (panel == AdministratorPanels.VIEW) {
            this.setViewPanel();
            width = 600;
            height = 250;
            title = "Administrator - View";
        }
        else if (panel == AdministratorPanels.ADD) {
            this.setAddPanel();
            width = 300;
            height = 200;
            title = "Administrator - ADD";
        }
        else if (panel == AdministratorPanels.DELETE) {
            this.setDeletePanel();
            width = 400;
            height = 125;
            title = "Administrator - Delete";
        }
        else if (panel == AdministratorPanels.REPORT) {
            this.setReportPanel();
            width = 600;
            height = 500;
            title = "Administrator - Report";
        }
        else if (panel == AdministratorPanels.REPORT_SEARCH) {
            this.setReportSearchPanel();
            width = 300;
            height = 150;
            title = "Administrator - Search";
        }

        this.setContentPane(this.mainPanel);
        this.pack();
        this.setSize(width, height);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.revalidate();
        this.repaint();
    }

    private void setMainPanel() {
        this.mainPanel.removeAll();
        clearTf();

        String[] columnNames = {"id", "Username", "Roles"};
        String[][] data = new String[this.users.size()][3];

        int i = 0;
        for (User user : this.users) {
            data[i][0] = String.valueOf(user.getId());
            data[i][1] = user.getUsername();
            data[i][2] = user.getRoles().stream().map(Role::getRole).collect(Collectors.joining(", "));

            i++;
        }

        JTable table = new JTable(data, columnNames);
        TableColumnModel columnModel = table.getColumnModel();
        table.setFillsViewportHeight(true);
        columnModel.getColumn(0).setPreferredWidth(55);
        columnModel.getColumn(1).setPreferredWidth(55);
        columnModel.getColumn(2).setPreferredWidth(55);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 500));

        JPanel buttonMenuPanel = new JPanel();
        buttonMenuPanel.setLayout(new FlowLayout());
        buttonMenuPanel.add(viewBtn);
        buttonMenuPanel.add(addBtn);
        buttonMenuPanel.add(deleteBtn);
        buttonMenuPanel.add(logoutBtn);

        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.mainPanel.add(buttonMenuPanel, BorderLayout.SOUTH);
    }

    private void setSearchPanel() {
        this.mainPanel.removeAll();

        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, Y_AXIS));
        this.mainPanel.add(new JLabel("id"));
        this.mainPanel.add(tfId);
        this.mainPanel.add(searchBtn);
        this.mainPanel.add(homeBtn);
    }

    private void setViewPanel() {
        this.mainPanel.removeAll();

        tfUsername.setText(searchedUser.getUsername());
        tfPassword.setText(searchedUser.getPassword());
        tfId.setText(String.valueOf(searchedUser.getId()));

        StringBuilder rights = new StringBuilder();
        for (Role role : searchedUser.getRoles()) {
            rights.append(Constants.getRolesRights().get(role.getRole()).stream().collect(Collectors.joining(", ")));
        }

        this.mainPanel.setLayout(new BoxLayout(mainPanel, Y_AXIS));

        this.mainPanel.add(new JLabel("id: " + searchedUser.getId()));
        this.mainPanel.add(new JLabel("Role: " + searchedUser.getRoles().stream().map(Role::getRole).collect(Collectors.joining(", "))));
        this.mainPanel.add(new JLabel("Rights: " + rights.toString()));
        this.mainPanel.add(new JLabel("Username:"));
        this.mainPanel.add(tfUsername);
        this.mainPanel.add(new JLabel("Password:"));
        this.mainPanel.add(tfPassword);
        this.mainPanel.add(updateBtn);
        this.mainPanel.add(reportSearchBtn);
        this.mainPanel.add(homeBtn);
    }

    private void setAddPanel() {
        this.mainPanel.removeAll();

        this.mainPanel.setLayout(new BoxLayout(mainPanel, Y_AXIS));

        this.mainPanel.add(new JLabel("Username: "));
        this.mainPanel.add(tfUsername);
        this.mainPanel.add(new JLabel("Password: "));
        this.mainPanel.add(tfPassword);
        this.mainPanel.add(new JLabel("Role:"));
        this.mainPanel.add(cbRole);

        this.mainPanel.add(confirmAddBtn);
        this.mainPanel.add(homeBtn);
    }

    private void setDeletePanel() {
        this.mainPanel.removeAll();

        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, Y_AXIS));
        this.mainPanel.add(new JLabel("id"));
        this.mainPanel.add(tfId);
        this.mainPanel.add(confirmDeleteBtn);
        this.mainPanel.add(homeBtn);
    }

    private void setReportPanel() {
        this.mainPanel.removeAll();
        clearTf();

        String[] columnNames = {"User", "Action", "Date"};
        String[][] data = new String[this.logs.size()][3];

        int i = 0;
        for (Log log : this.logs) {
            data[i][0] = log.getUser();
            data[i][1] = log.getAction();
            data[i][2] = log.getDate().toString();

            i++;
        }

        JTable table = new JTable(data, columnNames);
        TableColumnModel columnModel = table.getColumnModel();
        table.setFillsViewportHeight(true);
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(65);
        columnModel.getColumn(2).setPreferredWidth(50);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 500));

        JPanel buttonMenuPanel = new JPanel();
        buttonMenuPanel.setLayout(new FlowLayout());
        buttonMenuPanel.add(homeBtn);

        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.mainPanel.add(buttonMenuPanel, BorderLayout.SOUTH);
    }

    private void setReportSearchPanel() {
        this.mainPanel.removeAll();

        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, Y_AXIS));
        this.mainPanel.add(new JLabel("Date from:"));
        this.mainPanel.add(tfDateFrom);
        this.mainPanel.add(new JLabel("Date to:"));
        this.mainPanel.add(tfDateTo);
        this.mainPanel.add(confirmReportBtn);
    }

    public void setViewButtonListener(ActionListener viewButtonListener) {
        viewBtn.addActionListener(viewButtonListener);
    }

    public void setAddButtonListener(ActionListener addButtonListener) {
        addBtn.addActionListener(addButtonListener);
    }

    public void setDeleteButtonListener(ActionListener deleteButtonListener) {
        deleteBtn.addActionListener(deleteButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        updateBtn.addActionListener(updateButtonListener);
    }

    public void setSearchButtonListener(ActionListener searchButtonListener) {
        searchBtn.addActionListener(searchButtonListener);
    }

    public void setConfirmAddButtonListener(ActionListener confirmAddButtonListener) {
        confirmAddBtn.addActionListener(confirmAddButtonListener);
    }

    public void setConfirmDeleteButtonListener(ActionListener confirmDeleteButtonListener) {
        confirmDeleteBtn.addActionListener(confirmDeleteButtonListener);
    }

    public void setConfirmReportBtn(ActionListener confirmReportButtonListener) {
        confirmReportBtn.addActionListener(confirmReportButtonListener);
    }

    public void setHomeButtonListener(ActionListener homeButtonListener) {
        homeBtn.addActionListener(homeButtonListener);
    }

    public void setLogoutButtonListener(ActionListener logoutButtonListener) {
        logoutBtn.addActionListener(logoutButtonListener);
    }

    public void setReportButtonListener(ActionListener reportButtonListener) {
        reportSearchBtn.addActionListener(reportButtonListener);
    }

    public AdministratorDTO getAdministratorDTO() {
        return new AdministratorDTOBuilder()
                .setId(tfId.getText())
                .setUsername(tfUsername.getText())
                .setPassword(tfPassword.getText())
                .setRole(String.valueOf(cbRole.getSelectedItem()))
                .setDateFrom(tfDateFrom.getText())
                .setDateTo(tfDateTo.getText())
                .build();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getSearchedUser() {
        return searchedUser;
    }

    public void setSearchedUser(User searchedUser) {
        this.searchedUser = searchedUser;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public void setVisible() {
        this.setVisible(true);
    }

    public void setNotVisible() {
        this.setVisible(false);
    }
}
