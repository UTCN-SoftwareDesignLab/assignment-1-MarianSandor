package repository.bill;

import model.Bill;
import model.Client;
import model.builder.BillBuilder;
import model.builder.ClientBuilder;
import repository.clientInfo.ClientInfoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.BILL;
import static database.Constants.Tables.CLIENT;

public class BillRepositoryMySQL implements BillRepository {

    private final Connection connection;

    public BillRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveBill(Bill bill) {
        try {
            PreparedStatement insertBillStatement = connection
                    .prepareStatement("INSERT INTO bill values (null, ?, ?, ?, ?)");
            insertBillStatement.setLong(1, bill.getClientId());
            insertBillStatement.setDouble(2, bill.getTotal());
            insertBillStatement.setDate(3, Date.valueOf(bill.getCreationDate()));
            insertBillStatement.setString(4, bill.getDetails());
            insertBillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Bill> getAllBills() {
        List<Bill> allBills = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchBillSql = "select * from " + BILL;
            ResultSet resultSet = statement.executeQuery(fetchBillSql);

            while(resultSet.next()) {
                allBills.add(new BillBuilder()
                        .setId(resultSet.getLong("id"))
                        .setClientId(resultSet.getLong("client_id"))
                        .setTotal(resultSet.getDouble("total"))
                        .setCreationDate(resultSet.getDate("creationDate").toLocalDate())
                        .setDetails(resultSet.getString("details"))
                        .build()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allBills;
    }

    @Override
    public List<Bill> getBillsByClient(Long clientId) {
        List<Bill> allBills = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchBillSql = "select * from " + BILL + " where `client_id`='" + clientId + "'";
            ResultSet resultSet = statement.executeQuery(fetchBillSql);

            while(resultSet.next()) {
                allBills.add(new BillBuilder()
                        .setId(resultSet.getLong("id"))
                        .setClientId(resultSet.getLong("client_id"))
                        .setTotal(resultSet.getDouble("total"))
                        .setCreationDate(resultSet.getDate("creationDate").toLocalDate())
                        .setDetails(resultSet.getString("details"))
                        .build()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allBills;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from bill where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
