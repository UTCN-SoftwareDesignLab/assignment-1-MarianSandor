package service.client;

import model.Client;
import model.ClientInfo;
import model.builder.ClientBuilder;
import model.builder.ClientInfoBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.client.ClientRepository;
import repository.clientInfo.ClientInfoRepository;
import repository.type.TypeRepository;
import view.dto.EmployeeDTO;

import java.time.LocalDate;
import java.util.List;

public class ClientServiceMySQL implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientInfoRepository clientInfoRepository;
    private final TypeRepository typeRepository;

    public ClientServiceMySQL(ClientRepository clientRepository, ClientInfoRepository clientInfoRepository, TypeRepository typeRepository) {
        this.clientRepository = clientRepository;
        this.clientInfoRepository = clientInfoRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAllClients();
    }

    @Override
    public Notification<Boolean> saveClient(EmployeeDTO employeeDTO) {
        Client client = new ClientBuilder()
                            .setUuid(employeeDTO.getUuid())
                            .setBalance(Double.parseDouble(employeeDTO.getBalance()))
                            .setCreationDate(LocalDate.now())
                            .setType(typeRepository.findTypeByName(employeeDTO.getType()).getId())
                            .build();
        ClientInfo clientInfo = new ClientInfoBuilder()
                                    .setName(employeeDTO.getName())
                                    .setIcn(employeeDTO.getIcn())
                                    .setPnc(employeeDTO.getPnc())
                                    .setAddress(employeeDTO.getAddress())
                                    .build();

        ClientValidator clientValidator = new ClientValidator(client, clientInfo);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientCreationNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientCreationNotification::addError);
            clientCreationNotification.setResult(Boolean.FALSE);
        } else {
            clientRepository.saveClient(client, clientInfo);
        }

        return clientCreationNotification;
    }

    @Override
    public Notification<Boolean> updateClient(EmployeeDTO employeeDTO, Client client, ClientInfo clientInfo) {
        Client newClient = new ClientBuilder()
                .setId(client.getId())
                .setUuid(client.getUuid())
                .setBalance(Double.parseDouble(employeeDTO.getBalance()))
                .setCreationDate(client.getCreationDate())
                .setType(client.getType())
                .setInfo(client.getInfo())
                .build();
        ClientInfo newClientInfo = new ClientInfoBuilder()
                .setId(clientInfo.getId())
                .setName(employeeDTO.getName())
                .setIcn(employeeDTO.getIcn())
                .setPnc(employeeDTO.getPnc())
                .setAddress(employeeDTO.getAddress())
                .build();

        ClientValidator clientValidator = new ClientValidator(newClient, newClientInfo);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientUpdateNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientUpdateNotification::addError);
            clientUpdateNotification.setResult(Boolean.FALSE);
        } else {
            clientRepository.updateClient(client.getId(), newClient);
            clientInfoRepository.updateInfo(clientInfo.getId(), newClientInfo);
        }

        return clientUpdateNotification;
    }

    @Override
    public void deleteClient(EmployeeDTO employeeDTO) {
        clientRepository.deleteClientById(Long.valueOf(employeeDTO.getId()));
    }

    @Override
    public void transferMoney(EmployeeDTO employeeDTO) {
        clientRepository.transfer(Long.valueOf(employeeDTO.getIdFrom()), Long.valueOf(employeeDTO.getIdTo()), Double.parseDouble(employeeDTO.getBalance()));
    }

    @Override
    public void payBill(EmployeeDTO employeeDTO) {
        Client client = clientRepository.findClientById(Long.valueOf(employeeDTO.getId()));
        Client newClient = new ClientBuilder()
                .setId(client.getId())
                .setUuid(client.getUuid())
                .setBalance(client.getBalance() - Double.parseDouble(employeeDTO.getBalance()))
                .setCreationDate(client.getCreationDate())
                .setType(client.getType())
                .setInfo(client.getInfo())
                .build();

        clientRepository.updateClient(client.getId(), newClient);
    }

    @Override
    public Client getClient(EmployeeDTO employeeDTO) {
        return clientRepository.findClientById(Long.valueOf(employeeDTO.getId()));
    }

    @Override
    public ClientInfo getClientInfo(EmployeeDTO employeeDTO) {
        Long clientInfoId = clientRepository.findClientById(Long.valueOf(employeeDTO.getId())).getInfo();
        return clientInfoRepository.findClientInfoById(clientInfoId);
    }
}
