package repository.client;

import database.JDBConnectionWrapper;
import model.Client;
import model.ClientInfo;
import model.builder.ClientBuilder;
import model.builder.ClientInfoBuilder;
import repository.clientInfo.ClientInfoRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static database.Constants.Tables.*;

public class ClientRepositoryMySQL implements ClientRepository {
    private final Connection connection;
    private final ClientInfoRepository clientInfoRepository;

    public ClientRepositoryMySQL(Connection connection, ClientInfoRepository clientInfoRepository) {
        this.connection = connection;
        this.clientInfoRepository = clientInfoRepository;
    }

    @Override
    public void saveClient(Client client, ClientInfo clientInfo) {
        try {
            client.setInfo(clientInfoRepository.addClientInfo(clientInfo));

            PreparedStatement insertClientStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?, ?)");
            insertClientStatement.setString(1, client.getUuid());
            insertClientStatement.setDouble(2, client.getBalance());
            insertClientStatement.setDate(3, Date.valueOf(client.getCreationDate()));
            insertClientStatement.setLong(4, client.getType());
            insertClientStatement.setLong(5, client.getInfo());
            insertClientStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClientById(Long clientId) {
        try {
            Client client = findClientById(clientId);
            clientInfoRepository.deleteClientInfoById(client.getInfo());

            Statement statement = connection.createStatement();
            String sql = "DELETE from " + CLIENT + " where `id`='" + clientId + "'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClient(Long clientId, Client newClient) {
        try {
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("UPDATE client SET `uuid`=?, `balance`=?, `type`=?, `info`=? WHERE `id`='" + clientId + "'");
            updateClientStatement.setString(1, newClient.getUuid());
            updateClientStatement.setDouble(2, newClient.getBalance());
            updateClientStatement.setLong(3, newClient.getType());
            updateClientStatement.setLong(4, newClient.getInfo());

            updateClientStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void transfer(Long fromClientId, Long toClientId, double amount) {
        Client newFromClient = findClientById(fromClientId);
        Client newToClient = findClientById(toClientId);

        newFromClient.setBalance(newFromClient.getBalance() - amount);
        newToClient.setBalance(newToClient.getBalance() + amount);

        updateClient(fromClientId, newFromClient);
        updateClient(toClientId, newToClient);
    }

    @Override
    public Client findClientById(Long clientId) {
        List<Client> all = findAllClients();

        List<Client> clients = all.stream()
                .filter(b -> b.getId().equals(clientId))
                .collect(Collectors.toList());

        return !clients.isEmpty() ? clients.get(0) : null;
    }

    @Override
    public List<Client> findAllClients() {
        List<Client> allClients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchClientSql = "select * from " + CLIENT;
            ResultSet resultSet = statement.executeQuery(fetchClientSql);

            while(resultSet.next()) {
                allClients.add(new ClientBuilder()
                                .setId(resultSet.getLong("id"))
                                .setUuid(resultSet.getString("uuid"))
                                .setBalance(resultSet.getDouble("balance"))
                                .setCreationDate(resultSet.getDate("creationDate").toLocalDate())
                                .setType(resultSet.getLong("type"))
                                .setInfo(resultSet.getLong("info"))
                                .build()
                            );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allClients;
    }

    @Override
    public void removeAll() {
        try {
            clientInfoRepository.removeAll();

            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
