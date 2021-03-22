package repository.log;

import database.JDBConnectionWrapper;
import model.ClientInfo;
import model.Log;
import model.builder.ClientInfoBuilder;
import model.builder.LogBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.clientInfo.ClientInfoRepositoryMySQL;

import java.time.LocalDate;
import java.util.List;

import static database.Constants.Schemas.TEST;
import static org.junit.Assert.*;

public class LogRepositoryMySQLTest {

    private static LogRepositoryMySQL repo;

    @Before
    public void setUp() throws Exception {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(TEST);
        repo = new LogRepositoryMySQL(connectionWrapper.getConnection());

        repo.removeAll();

        Log log = new LogBuilder()
                .setUser("test")
                .setAction("test")
                .setDate(LocalDate.now())
                .build();
        repo.log(log);
    }

    @Test
    public void log() {
        String user = "Test2";
        Log log = new LogBuilder()
                .setUser(user)
                .setAction("test2")
                .setDate(LocalDate.now())
                .build();
        repo.log(log);

        List<Log> foundLogs = repo.findLogsByUser(user);

        assertEquals(1, foundLogs.size());
    }

    @Test
    public void findAll() {
        List<Log> foundLogs = repo.findAll();

        assertEquals(1, foundLogs.size());
    }

    @Test
    public void findLogsByUser() {
        List<Log> foundLogs = repo.findLogsByUser("test");

        assertEquals(1, foundLogs.size());
    }

    @Test
    public void removeAll() {
        repo.removeAll();

        List<Log> foundLogs = repo.findAll();

        assertEquals(0, foundLogs.size());
    }
}