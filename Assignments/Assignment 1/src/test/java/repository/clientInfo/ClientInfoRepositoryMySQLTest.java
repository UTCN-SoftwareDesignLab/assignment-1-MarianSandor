package repository.clientInfo;

import database.JDBConnectionWrapper;
import model.Client;
import model.ClientInfo;
import model.builder.ClientBuilder;
import model.builder.ClientInfoBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.client.ClientRepositoryMySQL;

import java.time.LocalDate;
import java.util.List;

import static database.Constants.Schemas.TEST;
import static org.junit.Assert.*;

public class ClientInfoRepositoryMySQLTest {
    private static ClientInfoRepositoryMySQL repo;

    @Before
    public void setUp() throws Exception {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(TEST);
        repo = new ClientInfoRepositoryMySQL(connectionWrapper.getConnection());

        repo.removeAll();

        ClientInfo clientInfo = new ClientInfoBuilder()
                                        .setName("test")
                                        .setAddress("test")
                                        .setIcn("test")
                                        .setPnc("test")
                                        .build();
        repo.addClientInfo(clientInfo);
    }

    @Test
    public void updateInfo() {
        ClientInfo clientInfo = repo.findAll().get(0);
        String newAddress = "newTest";

        ClientInfo newClientInfo = new ClientInfoBuilder()
                                        .setIcn(clientInfo.getIcn())
                                        .setName(clientInfo.getName())
                                        .setPnc(clientInfo.getPnc())
                                        .setAddress(newAddress)
                                        .build();

        repo.updateInfo(clientInfo.getId(), newClientInfo);

        clientInfo = repo.findClientInfoById(clientInfo.getId());

        assertEquals(clientInfo.getAddress(), newAddress);
    }

    @Test
    public void findClientInfoById() {
        ClientInfo clientInfo = repo.findAll().get(0);

        ClientInfo sameClientInfo = repo.findClientInfoById(clientInfo.getId());

        assertNotNull(sameClientInfo);
    }

    @Test
    public void addClientInfo() {
        ClientInfo clientInfo = new ClientInfoBuilder()
                                    .setName("test")
                                    .setAddress("test")
                                    .setIcn("test")
                                    .setPnc("test")
                                    .build();
        repo.addClientInfo(clientInfo);

        List<ClientInfo> all = repo.findAll();

        assertEquals(2, all.size());
    }

    @Test
    public void deleteClientInfoById() {
        Long id = repo.findAll().get(0).getId();

        repo.deleteClientInfoById(id);

        List<ClientInfo> all = repo.findAll();
        assertEquals(0, all.size());
    }

    @Test
    public void removeAll() {
        repo.removeAll();
        List<ClientInfo> all = repo.findAll();

        assertEquals(0, all.size());
    }
}