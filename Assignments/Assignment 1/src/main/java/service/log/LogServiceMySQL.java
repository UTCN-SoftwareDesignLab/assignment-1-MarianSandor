package service.log;

import model.Log;
import model.User;
import repository.log.LogRepository;
import repository.user.UserRepository;
import view.dto.AdministratorDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LogServiceMySQL implements LogService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;

    public LogServiceMySQL(LogRepository logRepository, UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void log(Log log) {
        logRepository.log(log);
    }

    @Override
    public List<Log> findLogsByUserAndDate(AdministratorDTO administratorDTO) {
        LocalDate dateFrom = LocalDate.parse(administratorDTO.getDateFrom());
        LocalDate dateTo = LocalDate.parse(administratorDTO.getDateTo());
        User user = userRepository.findById(Long.valueOf(administratorDTO.getId()));

        List<Log> logs = logRepository.findLogsByUser(user.getUsername());

        logs = logs.stream()
                .filter(log -> log.getDate().equals(dateFrom) ||
                                            log.getDate().equals(dateTo) ||
                                    log.getDate().isAfter(dateFrom) && log.getDate().isBefore(dateTo))
                .collect(Collectors.toList());

        return logs;
    }
}
