package repository.client;

import model.Client;
import model.ClientInfo;

import java.util.List;

public interface ClientRepository {

    void saveClient(Client client, ClientInfo clientInfo);

    void deleteClientById(Long clientId);

    void updateClient(Long clientId, Client newClient);

    Client findClientById(Long clientId);

    List<Client> findAllClients();

    void transfer(Long fromClientId, Long toClientId, double amount);

    void removeAll();
}
