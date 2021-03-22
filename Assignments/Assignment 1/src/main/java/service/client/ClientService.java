package service.client;

import model.Client;
import model.ClientInfo;
import model.validation.Notification;
import view.dto.EmployeeDTO;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Notification<Boolean> saveClient(EmployeeDTO employeeDTO);

    Notification<Boolean> updateClient(EmployeeDTO employeeDTO, Client client, ClientInfo clientInfo);

    void deleteClient(EmployeeDTO employeeDTO);

    void transferMoney(EmployeeDTO employeeDTO);

    void payBill(EmployeeDTO employeeDTO);

    Client getClient(EmployeeDTO employeeDTO);

    ClientInfo getClientInfo(EmployeeDTO employeeDTO);
}
