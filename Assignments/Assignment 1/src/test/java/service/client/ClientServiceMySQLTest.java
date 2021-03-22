package service.client;

import database.JDBConnectionWrapper;
import model.Client;
import model.ClientInfo;
import model.builder.ClientBuilder;
import model.builder.ClientInfoBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.clientInfo.ClientInfoRepository;
import repository.clientInfo.ClientInfoRepositoryMySQL;
import repository.type.TypeRepository;
import repository.type.TypeRepositoryMySQL;
import view.dto.EmployeeDTO;
import view.dto.EmployeeDTOBuilder;

import java.time.LocalDate;
import java.util.List;

import static database.Constants.Schemas.TEST;
import static org.junit.Assert.*;

public class ClientServiceMySQLTest {

    private static ClientService clientService;
    private static Client dummyClient;
    private static ClientInfo dummyClientInfo;

    @Before
    public void setUp() throws Exception {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(TEST);
        ClientInfoRepository clientInfoRepository = new ClientInfoRepositoryMySQL(connectionWrapper.getConnection());
        ClientRepository clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection(), clientInfoRepository);
        TypeRepository typeRepository = new TypeRepositoryMySQL(connectionWrapper.getConnection());
        clientService = new ClientServiceMySQL(clientRepository, clientInfoRepository, typeRepository);

        clientRepository.removeAll();
        clientInfoRepository.removeAll();

        dummyClient = new ClientBuilder()
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

        clientRepository.saveClient(dummyClient, dummyClientInfo);
        dummyClientInfo = clientInfoRepository.findAll().get(0);
        dummyClient = clientRepository.findAllClients().get(0);
    }

    @Test
    public void findAll() {
        List<Client> all = clientService.findAll();

        assertEquals(1, all.size());
    }

    @Test
    public void saveClient() {
        clientService.saveClient(new EmployeeDTOBuilder()
                .setUuid("test")
                .setBalance("123")
                .setType("credit")
                .build()
        );

        List<Client> all = clientService.findAll();

        assertEquals(2, all.size());
    }

    @Test
    public void updateClient() {
        String newBalance = "321.0";
        clientService.updateClient(new EmployeeDTOBuilder()
                .setBalance(newBalance)
                .build(),
                dummyClient,
                dummyClientInfo
        );

        Client client = clientService.findAll().get(0);

        assertEquals(newBalance, String.valueOf(client.getBalance()));
    }

    @Test
    public void deleteClient() {
        Long id = clientService.findAll().get(0).getId();

        clientService.deleteClient(new EmployeeDTOBuilder()
                .setId(id.toString())
                .build()
        );

        List<Client> all = clientService.findAll();

        assertTrue(all.isEmpty());
    }

    @Test
    public void transferMoney() {
        double balance = 100.0;
        clientService.saveClient(new EmployeeDTOBuilder()
                .setUuid("test")
                .setBalance("123")
                .setType("credit")
                .build()
        );

        List<Client> beforeAll = clientService.findAll();

        clientService.transferMoney(new EmployeeDTOBuilder()
                .setIdTo(beforeAll.get(0).getId().toString())
                .setIdFrom(beforeAll.get(1).getId().toString())
                .setBalance(String.valueOf(balance))
                .build()
        );

        List<Client> all = clientService.findAll();

        assertEquals(String.valueOf(beforeAll.get(1).getBalance() - balance), String.valueOf(all.get(1).getBalance()));
    }

    @Test
    public void payBill() {
        Double total = 1.23;

        clientService.payBill(new EmployeeDTOBuilder()
                .setId(dummyClient.getId().toString())
                .setBalance(total.toString())
                .build()
        );

        Client client = clientService.findAll().get(0);

        assertEquals(String.valueOf(dummyClient.getBalance() - total), String.valueOf(client.getBalance()));
    }

    @Test
    public void getClient() {
        Long id = clientService.findAll().get(0).getId();

        Client client = clientService.getClient(new EmployeeDTOBuilder()
                .setId(id.toString())
                .build()
        );

        List<Client> all = clientService.findAll();

        assertEquals(all.get(0).getId(), client.getId());
    }

    @Test
    public void getClientInfo() {
        Long id = clientService.findAll().get(0).getId();

        ClientInfo clientInfo = clientService.getClientInfo(new EmployeeDTOBuilder()
                .setId(id.toString())
                .build()
        );

        List<Client> all = clientService.findAll();

        assertEquals(all.get(0).getInfo(), clientInfo.getId());
    }
}