package service.log;

import model.Log;
import view.dto.AdministratorDTO;

import java.util.List;

public interface LogService {

    void log(Log log);

    List<Log> findLogsByUserAndDate(AdministratorDTO administratorDTO);
}
