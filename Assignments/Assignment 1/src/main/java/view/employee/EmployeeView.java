package view.employee;

import database.Constants;
import model.Client;
import model.ClientInfo;
import view.dto.EmployeeDTO;
import view.dto.EmployeeDTOBuilder;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    private JPanel mainPanel;
    private JButton viewBtn;
    private JButton addBtn;
    private JButton deleteBtn;
    private JButton updateBtn;
    private JButton transferBtn;
    private JButton processBtn;
    private JButton searchBtn;
    private JButton confirmAddBtn;
    private JButton confirmDeleteBtn;
    private JButton confirmTransferBtn;
    private JButton confirmProcessBtn;
    private JButton homeBtn;
    private JButton logoutBtn;
    private JTextField tfId;
    private JTextField tfIdFrom;
    private JTextField tfIdTo;
    private JTextField tfBalance;
    private JTextField tfUuid;
    private JComboBox<String> cbType;
    private JTextField tfName;
    private JTextField tfIcn;
    private JTextField tfPnc;
    private JTextField tfAddress;
    private JTextField tfDetails;
    private List<Client> clients;
    private Client searchedClient;
    private ClientInfo searchedClientInfo;

    public EmployeeView() throws HeadlessException {
        initializeFields();
    }

    private void initializeFields() {
        mainPanel = new JPanel();
        clients = new ArrayList<>();
        viewBtn = new JButton("View Info");
        addBtn = new JButton("Add Client");
        deleteBtn = new JButton("Delete Client");
        updateBtn = new JButton("Update");
        transferBtn = new JButton("Transfer");
        processBtn = new JButton("Process Bill");
        searchBtn = new JButton("Search");
        confirmAddBtn = new JButton("Confirm");
        confirmDeleteBtn = new JButton("Confirm");
        confirmTransferBtn = new JButton("Confirm");
        confirmProcessBtn = new JButton("Confirm");
        homeBtn = new JButton("Home");
        logoutBtn = new JButton("Logout");
        tfId = new JTextField("");
        tfIdTo = new JTextField("");
        tfIdFrom = new JTextField("");
        tfBalance = new JTextField("");
        tfUuid = new JTextField("");
        tfAddress = new JTextField("");
        tfName = new JTextField("");
        tfIcn = new JTextField("");
        tfPnc = new JTextField("");
        tfDetails = new JTextField("");
        cbType = new JComboBox<>(Constants.Types.TYPES);
    }

    private void clearTf() {
        tfId.setText("");
        tfIdTo.setText("");
        tfIdFrom.setText("");
        tfBalance.setText("");
        tfUuid.setText("");
        tfAddress.setText("");
        tfName.setText("");
        tfIcn.setText("");
        tfPnc.setText("");
        tfDetails.setText("");
    }

    public void setPanel(EmployeePanels panel) {
        int width = 0;
        int height = 0;
        String title = null;

        if (panel == EmployeePanels.MAIN) {
            this.setMainPanel();
            width = 600;
            height = 500;
            title = "Employee - Home";
        }else if (panel == EmployeePanels.SEARCH) {
            this.setSearchPanel();
            width = 400;
            height = 125;
            title = "Employee - Search";
        }
        else if (panel == EmployeePanels.VIEW) {
            this.setViewPanel();
            width = 300;
            height = 400;
            title = "Employee - View";
        }
        else if (panel == EmployeePanels.ADD) {
            this.setAddPanel();
            width = 300;
            height = 400;
            title = "Employee - ADD";
        }
        else if (panel == EmployeePanels.DELETE) {
            this.setDeletePanel();
            width = 400;
            height = 125;
            title = "Employee - Delete";
        }
        else if (panel == EmployeePanels.TRANSFER) {
            this.setTransferPanel();
            width = 400;
            height = 200;
            title = "Employee - Transfer";
        }
        else if (panel == EmployeePanels.PROCESS) {
            this.setProcessPanel();
            width = 400;
            height = 200;
            title = "Employee - Process";
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

        String[] columnNames = {"id", "UUID", "Type", "Balance", "Creation Date"};
        String[][] data = new String[this.clients.size()][5];

        int i = 0;
        for (Client client : this.clients) {
            data[i][0] = String.valueOf(client.getId());
            data[i][1] = String.valueOf(client.getUuid());
            data[i][2] = Constants.Types.TYPES[client.getType().intValue() - 1];
            data[i][3] = String.valueOf(client.getBalance());
            data[i][4] = client.getCreationDate().toString();

            i++;
        }

        JTable table = new JTable(data, columnNames);
        TableColumnModel columnModel = table.getColumnModel();
        table.setFillsViewportHeight(true);
        columnModel.getColumn(0).setPreferredWidth(55);
        columnModel.getColumn(1).setPreferredWidth(55);
        columnModel.getColumn(2).setPreferredWidth(55);
        columnModel.getColumn(3).setPreferredWidth(55);
        columnModel.getColumn(4).setPreferredWidth(55);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 500));

        JPanel buttonMenuPanel = new JPanel();
        buttonMenuPanel.setLayout(new FlowLayout());
        buttonMenuPanel.add(viewBtn);
        buttonMenuPanel.add(addBtn);
        buttonMenuPanel.add(deleteBtn);
        buttonMenuPanel.add(transferBtn);
        buttonMenuPanel.add(processBtn);
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

        tfBalance.setText(String.valueOf(searchedClient.getBalance()));
        tfName.setText(searchedClientInfo.getName());
        tfIcn.setText(searchedClientInfo.getIcn());
        tfPnc.setText(searchedClientInfo.getPnc());
        tfAddress.setText(searchedClientInfo.getAddress());
        tfUuid.setText(searchedClient.getUuid());
        tfId.setText(String.valueOf(searchedClient.getId()));

        this.mainPanel.setLayout(new BoxLayout(mainPanel, Y_AXIS));

        this.mainPanel.add(new JLabel("id: " + searchedClient.getId()));
        this.mainPanel.add(new JLabel("UUID: " + searchedClient.getUuid()));
        this.mainPanel.add(new JLabel("Type: " + Constants.Types.TYPES[searchedClient.getType().intValue()]));
        this.mainPanel.add(new JLabel("Creation Date: " + searchedClient.getCreationDate().toString()));
        this.mainPanel.add(new JLabel("Balance:"));
        this.mainPanel.add(tfBalance);
        this.mainPanel.add(new JLabel("Name:"));
        this.mainPanel.add(tfName);
        this.mainPanel.add(new JLabel("ICN:"));
        this.mainPanel.add(tfIcn);
        this.mainPanel.add(new JLabel("PNC:"));
        this.mainPanel.add(tfPnc);
        this.mainPanel.add(new JLabel("Address:"));
        this.mainPanel.add(tfAddress);
        this.mainPanel.add(updateBtn);
        this.mainPanel.add(homeBtn);
    }

    private void setAddPanel() {
        this.mainPanel.removeAll();

        this.mainPanel.setLayout(new BoxLayout(mainPanel, Y_AXIS));

        this.mainPanel.add(new JLabel("UUID: "));
        this.mainPanel.add(tfUuid);
        this.mainPanel.add(new JLabel("Type: "));
        this.mainPanel.add(cbType);
        this.mainPanel.add(new JLabel("Balance:"));
        this.mainPanel.add(tfBalance);
        this.mainPanel.add(new JLabel("Name:"));
        this.mainPanel.add(tfName);
        this.mainPanel.add(new JLabel("ICN:"));
        this.mainPanel.add(tfIcn);
        this.mainPanel.add(new JLabel("PNC:"));
        this.mainPanel.add(tfPnc);
        this.mainPanel.add(new JLabel("Address:"));
        this.mainPanel.add(tfAddress);
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

    private void setTransferPanel() {
        this.mainPanel.removeAll();

        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, Y_AXIS));
        this.mainPanel.add(new JLabel("id From:"));
        this.mainPanel.add(tfIdFrom);
        this.mainPanel.add(new JLabel("id To:"));
        this.mainPanel.add(tfIdTo);
        this.mainPanel.add(new JLabel("Amount:"));
        this.mainPanel.add(tfBalance);
        this.mainPanel.add(confirmTransferBtn);
        this.mainPanel.add(homeBtn);
    }

    private void setProcessPanel() {
        this.mainPanel.removeAll();

        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, Y_AXIS));
        this.mainPanel.add(new JLabel("Client id:"));
        this.mainPanel.add(tfId);
        this.mainPanel.add(new JLabel("Total:"));
        this.mainPanel.add(tfBalance);
        this.mainPanel.add(new JLabel("Details:"));
        this.mainPanel.add(tfDetails);
        this.mainPanel.add(confirmProcessBtn);
        this.mainPanel.add(homeBtn);
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

    public void setTransferButtonListener(ActionListener TransferButtonListener) {
        transferBtn.addActionListener(TransferButtonListener);
    }

    public void setProcessButtonListener(ActionListener processButtonListener) {
        processBtn.addActionListener(processButtonListener);
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

    public void setConfirmTransferButtonListener(ActionListener confirmTransferButtonListener) {
        confirmTransferBtn.addActionListener(confirmTransferButtonListener);
    }

    public void setConfirmProcessButtonListener(ActionListener confirmProcessButtonListener) {
        confirmProcessBtn.addActionListener(confirmProcessButtonListener);
    }

    public void setHomeButtonListener(ActionListener homeButtonListener) {
        homeBtn.addActionListener(homeButtonListener);
    }

    public void setLogoutButtonListener(ActionListener logoutButtonListener) {
        logoutBtn.addActionListener(logoutButtonListener);
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Client getSearchedClient() {
        return searchedClient;
    }

    public void setSearchedClient(Client searchedClient) {
        this.searchedClient = searchedClient;
    }

    public ClientInfo getSearchedClientInfo() {
        return searchedClientInfo;
    }

    public void setSearchedClientInfo(ClientInfo searchedClientInfo) {
        this.searchedClientInfo = searchedClientInfo;
    }

    public EmployeeDTO getEmployeeDTO() {
        return new EmployeeDTOBuilder()
                    .setId(tfId.getText())
                    .setIdTo(tfIdTo.getText())
                    .setIdFrom(tfIdFrom.getText())
                    .setBalance(tfBalance.getText())
                    .setType(String.valueOf(cbType.getSelectedItem()))
                    .setUuid(tfUuid.getText())
                    .setAddress(tfAddress.getText())
                    .setName(tfName.getText())
                    .setPnc(tfPnc.getText())
                    .setIcn(tfIcn.getText())
                    .setDetails(tfDetails.getText())
                    .build();
    }

    public void setVisible() {
        this.setVisible(true);
    }

    public void setNotVisible() {
        this.setVisible(false);
    }

}
