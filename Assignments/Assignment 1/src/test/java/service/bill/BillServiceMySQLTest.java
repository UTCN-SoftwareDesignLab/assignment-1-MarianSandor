package service.bill;

import database.JDBConnectionWrapper;
import model.Bill;
import model.Client;
import model.ClientInfo;
import model.builder.ClientBuilder;
import model.builder.ClientInfoBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.bill.BillRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.clientInfo.ClientInfoRepository;
import repository.clientInfo.ClientInfoRepositoryMySQL;
import view.dto.EmployeeDTO;
import view.dto.EmployeeDTOBuilder;

import java.time.LocalDate;
import java.util.List;

import static database.Constants.Schemas.TEST;
import static org.junit.Assert.*;

public class BillServiceMySQLTest {

    private static BillServiceMySQL service;
    private static ClientInfoRepository clientInfoRepository;
    private static ClientRepository clientRepository;
    private static Client dummyClient;
    private static ClientInfo dummyClientInfo;

    @Before
    public void setUp() throws Exception {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(TEST);
        BillRepositoryMySQL billRepositoryMySQL = new BillRepositoryMySQL(connectionWrapper.getConnection());
        service = new BillServiceMySQL(billRepositoryMySQL);
        clientInfoRepository = new ClientInfoRepositoryMySQL(connectionWrapper.getConnection());
        clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection(), clientInfoRepository);

        billRepositoryMySQL.removeAll();

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

        service.addBill(new EmployeeDTOBuilder()
                .setId(dummyClient.getId().toString())
                .setBalance("123")
                .setDetails("")
                .build()
        );
    }

    @Test
    public void addBill() {
        service.addBill(new EmployeeDTOBuilder()
                .setId(dummyClient.getId().toString())
                .setBalance("23")
                .setDetails("")
                .build()
        );

        List<Bill> bills = service.getAllBills();

        assertEquals(2, bills.size());
    }

    @Test
    public void getBillsByClient() {
        List<Bill> bills = service.getBillsByClient(new EmployeeDTOBuilder()
                                                        .setId(dummyClient.getId().toString())
                                                        .build()
        );

        assertEquals(1, bills.size());
    }

    @Test
    public void getAllBills() {
        List<Bill> bills = service.getAllBills();

        assertEquals(1, bills.size());
    }
}