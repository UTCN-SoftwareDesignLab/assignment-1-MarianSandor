package repository.client;

import database.JDBConnectionWrapper;
import model.Client;
import model.ClientInfo;
import model.builder.ClientBuilder;
import model.builder.ClientInfoBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.clientInfo.ClientInfoRepositoryMySQL;

import java.time.LocalDate;
import java.util.List;

import static database.Constants.Schemas.TEST;
import static org.junit.Assert.*;

public class ClientRepositoryMySQLTest {
    private static ClientRepositoryMySQL clientRepo;
    private static ClientInfoRepositoryMySQL clientInfoRepo;
    private static ClientInfo dummyClientInfo;

    @Before
    public void setUp() throws Exception {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(TEST);
        clientInfoRepo = new ClientInfoRepositoryMySQL(connectionWrapper.getConnection());
        clientRepo = new ClientRepositoryMySQL(connectionWrapper.getConnection(), clientInfoRepo);

        clientRepo.removeAll();
        clientInfoRepo.removeAll();

        Client client = new ClientBuilder()
                .setCreationDate(LocalDate.now())
                .setUuid("test")
                .setBalance(11.11)
                .setType(1L)
                .build();

        dummyClientInfo = new ClientInfoBuilder()
                .setAddress("")
                .setPnc("")
                .setIcn("")
                .setName("")
                .build();

        clientRepo.saveClient(client, dummyClientInfo);
    }

    @Test
    public void saveClient() {
        Client client = new ClientBuilder()
                .setCreationDate(LocalDate.now())
                .setUuid("This is an uuid")
                .setBalance(0.0)
                .setType(1L)
                .build();

        clientRepo.saveClient(client, dummyClientInfo);
        List<Client> all = clientRepo.findAllClients();
        assertEquals(2, all.size());
    }

    @Test
    public void deleteClientById() {
        List<Client> all = clientRepo.findAllClients();
        clientRepo.deleteClientById(all.get(0).getId());

        all = clientRepo.findAllClients();
        assertEquals(0, all.size());
    }

    @Test
    public void updateClient() {
        Client client = clientRepo.findAllClients().get(0);
        double newBalance = client.getBalance() + 10.0;
        Client newClient = new ClientBuilder()
                            .setId(client.getId())
                            .setBalance(newBalance)
                            .setInfo(client.getInfo())
                            .setUuid(client.getUuid())
                            .setType(client.getType())
                            .setCreationDate(client.getCreationDate())
                            .build();
        clientRepo.updateClient(client.getId(), newClient);

        client = clientRepo.findClientById(newClient.getId());

        assertEquals(client.getBalance(), newBalance, 0.1);
    }

    @Test
    public void findClientById() {
        List<Client> all = clientRepo.findAllClients();
        Long currId = all.get(0).getId();

        Client client = new ClientBuilder()
                .setCreationDate(LocalDate.now())
                .setUuid("This is an uuid")
                .setBalance(0.0)
                .setType(1L)
                .build();

        clientRepo.saveClient(client, dummyClientInfo);

        client = clientRepo.findClientById(currId + 1);

        assertNotNull(client);
    }

    @Test
    public void findAllClients() {
        List<Client> all = clientRepo.findAllClients();

        assertEquals(1, all.size());
    }

    @Test
    public void removeAll() {
        clientRepo.removeAll();
        List<Client> all = clientRepo.findAllClients();

        assertEquals(0, all.size());
    }
}