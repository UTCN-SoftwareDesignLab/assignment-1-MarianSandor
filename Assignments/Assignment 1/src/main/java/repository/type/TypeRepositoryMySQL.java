package repository.type;

import model.Type;

import java.sql.*;

import static database.Constants.Tables.*;

public class TypeRepositoryMySQL implements TypeRepository {
    private final Connection connection;

    public TypeRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addType(String type) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + TYPE + " values (null, ?)");
            insertStatement.setString(1, type);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public Type findTypeById(Long typeId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchTypeSql = "select * from " + TYPE + " where `id`='" + typeId + "'";
            ResultSet typeResultSet = statement.executeQuery(fetchTypeSql);
            typeResultSet.next();
            String typeName = typeResultSet.getString("name");

            return new Type(typeId, typeName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Type findTypeByName(String name) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchTypeSql = "select * from " + TYPE + " where `name`='" + name + "'";
            ResultSet typeResultSet = statement.executeQuery(fetchTypeSql);
            typeResultSet.next();
            Long typeId = typeResultSet.getLong("id");

            return new Type(typeId, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Type findTypeForClient(Long clientId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchTypeSql = "select * from " + CLIENT + " where `id`='" + clientId + "'";
            ResultSet typeResultSet = statement.executeQuery(fetchTypeSql);
            typeResultSet.next();
            Long typeId = typeResultSet.getLong("type");

            return findTypeById(typeId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
