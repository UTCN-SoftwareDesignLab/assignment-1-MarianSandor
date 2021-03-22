package repository.bill;

import database.JDBConnectionWrapper;
import model.*;
import model.builder.BillBuilder;
import model.builder.ClientBuilder;
import model.builder.ClientInfoBuilder;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.clientInfo.ClientInfoRepository;
import repository.clientInfo.ClientInfoRepositoryMySQL;
import repository.roles.RightsRolesRepository;
import repository.roles.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Schemas.TEST;
import static org.junit.Assert.*;

public class BillRepositoryMySQLTest {

    private static BillRepository billRepository;
    private static ClientInfoRepository clientInfoRepository;
    private static ClientRepository clientRepository;
    private static Client dummyClient;
    private static ClientInfo dummyClientInfo;

    @Before
    public void setUp() throws Exception {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(TEST);
        billRepository = new BillRepositoryMySQL(connectionWrapper.getConnection());
        clientInfoRepository = new ClientInfoRepositoryMySQL(connectionWrapper.getConnection());
        clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection(), clientInfoRepository);

        billRepository.removeAll();
        clientRepository.removeAll();
        clientInfoRepository.removeAll();

        dummyClientInfo = new ClientInfoBuilder()
                .setAddress("")
                .setPnc("")
                .setIcn("")
                .setName("")
                .build();

        clientRepository.saveClient(new ClientBuilder()
                            .setCreationDate(LocalDate.now())
                            .setUuid("This is an uuid")
                            .setBalance(0.0)
                            .setType(1L)
                            .build(),
                            dummyClientInfo
        );

        dummyClient = clientRepository.findAllClients().get(0);

        billRepository.saveBill(new BillBuilder()
                        .setId(2L)
                        .setClientId(dummyClient.getId())
                        .setTotal(123.43)
                        .setCreationDate(LocalDate.now())
                        .setDetails("this is a test bill")
                        .build()
        );
    }

    @Test
    public void saveBill() {
        billRepository.saveBill(new BillBuilder()
                .setId(1L)
                .setClientId(dummyClient.getId())
                .setTotal(123.43)
                .setCreationDate(LocalDate.now())
                .setDetails("this is another test bill")
                .build()
        );

        List<Bill> bills = billRepository.getAllBills();

        assertEquals(2, bills.size());
    }

    @Test
    public void getAllBills() {
        List<Bill> bills = billRepository.getAllBills();

        assertEquals(1, bills.size());
    }

    @Test
    public void getBillsByUser() {
        List<Bill> bills = billRepository.getBillsByClient(dummyClient.getId());

        assertEquals(1, bills.size());
    }

    @Test
    public void removeAll() {
        billRepository.removeAll();

        List<Bill> bills = billRepository.getBillsByClient(1L);

        assertEquals(0, bills.size());
    }
}