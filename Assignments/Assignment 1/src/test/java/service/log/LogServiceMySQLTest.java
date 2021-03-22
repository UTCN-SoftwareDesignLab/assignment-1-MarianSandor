package service.log;

import database.JDBConnectionWrapper;
import model.Log;
import model.User;
import model.builder.LogBuilder;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.bill.BillRepositoryMySQL;
import repository.log.LogRepositoryMySQL;
import repository.roles.RightsRolesRepository;
import repository.roles.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.bill.BillServiceMySQL;
import view.dto.AdministratorDTO;
import view.dto.AdministratorDTOBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Schemas.TEST;
import static org.junit.Assert.*;

public class LogServiceMySQLTest {

    private static LogService logService;
    private static User user;

    @Before
    public void setUp() throws Exception {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(TEST);
        LogRepositoryMySQL logRepositoryMySQL = new LogRepositoryMySQL(connectionWrapper.getConnection());
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
        UserRepository userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
        logService = new LogServiceMySQL(logRepositoryMySQL, userRepository);
        user = new User();

        logRepositoryMySQL.removeAll();
        userRepository.removeAll();

        userRepository.save(new UserBuilder()
                .setUsername("test")
                .setPassword("test")
                .setRoles(new ArrayList<>())
                .build()
        );

        user = userRepository.findAll().get(0);
        logService.log(new LogBuilder()
                            .setDate(LocalDate.now())
                            .setUser("test")
                            .setAction("test_action")
                            .build()
        );
    }

    @Test
    public void log() {
        logService.log(new LogBuilder()
                .setDate(LocalDate.now())
                .setUser(user.getId().toString())
                .setAction("test_action1")
                .build()
        );

        List<Log> logs = logService.findLogsByUserAndDate(new AdministratorDTOBuilder()
                                                                .setId(user.getId().toString())
                                                                .setDateFrom(LocalDate.now().toString())
                                                                .setDateTo(LocalDate.now().toString())
                                                                .build()
        );

        assertEquals(1, logs.size());
    }

    @Test
    public void findLogsByUserAndDate() {
        List<Log> logs = logService.findLogsByUserAndDate(new AdministratorDTOBuilder()
                .setId(user.getId().toString())
                .setDateFrom(LocalDate.now().toString())
                .setDateTo(LocalDate.now().toString())
                .build()
        );

        assertEquals(1, logs.size());
    }
}