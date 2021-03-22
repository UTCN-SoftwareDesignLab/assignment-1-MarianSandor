package repository.clientInfo;

import model.Client;
import model.ClientInfo;
import model.builder.ClientBuilder;
import model.builder.ClientInfoBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.CLIENT_INFO;

public class ClientInfoRepositoryMySQL implements ClientInfoRepository {

    private final Connection connection;

    public ClientInfoRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void updateInfo(Long clientInfoId, ClientInfo newClientInfo) {
        try {
            PreparedStatement updateClientInfoStatement = connection
                    .prepareStatement("UPDATE client_info SET `name`=?, `icn`=?, `pnc`=?, `address`=? WHERE `id`='" + clientInfoId + "'");
            updateClientInfoStatement.setString(1, newClientInfo.getName());
            updateClientInfoStatement.setString(2, newClientInfo.getIcn());
            updateClientInfoStatement.setString(3, newClientInfo.getPnc());
            updateClientInfoStatement.setString(4, newClientInfo.getAddress());

            updateClientInfoStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ClientInfo findClientInfoById(Long clientInfoId) {
        try {
            Statement statement = connection.createStatement();
            String fetchClientInfoSql = "select * from " + CLIENT_INFO + " where `id`='" + clientInfoId + "'";
            ResultSet resultSet = statement.executeQuery(fetchClientInfoSql);
            resultSet.next();

            return new ClientInfoBuilder()
                    .setId(resultSet.getLong("id"))
                    .setName(resultSet.getString("name"))
                    .setIcn(resultSet.getString("icn"))
                    .setPnc(resultSet.getString("pnc"))
                    .setAddress(resultSet.getString("address"))
                    .build();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Long addClientInfo(ClientInfo clientInfo) {
        try {
            PreparedStatement insertClientInfoStatement = connection
                    .prepareStatement("INSERT INTO client_info values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertClientInfoStatement.setString(1, clientInfo.getName());
            insertClientInfoStatement.setString(2, clientInfo.getIcn());
            insertClientInfoStatement.setString(3, clientInfo.getPnc());
            insertClientInfoStatement.setString(4, clientInfo.getAddress());
            insertClientInfoStatement.executeUpdate();

            ResultSet rs = insertClientInfoStatement.getGeneratedKeys();
            rs.next();

            return rs.getLong(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1L;
    }

    @Override
    public void deleteClientInfoById(Long clientInfoId) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from " + CLIENT_INFO + " where `id`='" + clientInfoId + "'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ClientInfo> findAll() {
        List<ClientInfo> all = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchClientInfoSql = "select * from " + CLIENT_INFO;
            ResultSet resultSet = statement.executeQuery(fetchClientInfoSql);

            while(resultSet.next()) {
                all.add(new ClientInfoBuilder()
                        .setId(resultSet.getLong("id"))
                        .setName(resultSet.getString("name"))
                        .setIcn(resultSet.getString("icn"))
                        .setPnc(resultSet.getString("pnc"))
                        .setAddress(resultSet.getString("address"))
                        .build()
                );

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return all;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client_info where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
