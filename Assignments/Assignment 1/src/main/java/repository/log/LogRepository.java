package repository.log;

import model.Log;

import java.util.List;

public interface LogRepository {

    void log(Log log);

    List<Log> findAll();

    List<Log> findLogsByUser(String user);

    void removeAll();
}
