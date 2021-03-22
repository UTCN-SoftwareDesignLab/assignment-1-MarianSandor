package repository.log;

import model.Bill;
import model.Log;
import model.builder.BillBuilder;
import model.builder.LogBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.BILL;
import static database.Constants.Tables.LOG;

public class LogRepositoryMySQL implements LogRepository {

    private final Connection connection;

    public LogRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void log(Log log) {
        try {
            PreparedStatement insertLogStatement = connection
                    .prepareStatement("INSERT INTO log values (null, ?, ?, ?)");
            insertLogStatement.setString(1, log.getUser());
            insertLogStatement.setString(2, log.getAction());
            insertLogStatement.setDate(3, Date.valueOf(log.getDate()));
            insertLogStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Log> findAll() {
        List<Log> allLogs = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchLogsSql = "select * from " + LOG;
            ResultSet resultSet = statement.executeQuery(fetchLogsSql);

            while(resultSet.next()) {
                allLogs.add(new LogBuilder()
                        .setUser(resultSet.getString("user"))
                        .setAction(resultSet.getString("action"))
                        .setDate(resultSet.getDate("date").toLocalDate())
                        .build()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allLogs;
    }

    @Override
    public List<Log> findLogsByUser(String user) {
        List<Log> allLogs = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchLogsSql = "select * from " + LOG + " where `user`='" + user + "'";
            ResultSet resultSet = statement.executeQuery(fetchLogsSql);

            while(resultSet.next()) {
                allLogs.add(new LogBuilder()
                        .setUser(resultSet.getString("user"))
                        .setAction(resultSet.getString("action"))
                        .setDate(resultSet.getDate("date").toLocalDate())
                        .build()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allLogs;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from log where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
