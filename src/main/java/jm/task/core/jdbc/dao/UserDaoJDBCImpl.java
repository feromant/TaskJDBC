package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(32), " +
                    "lastname VARCHAR(32), " +
                    "age TINYINT)");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.prepareStatement(
                    "INSERT INTO users (name, lastname, age) VALUES(?, ?, ?)");

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            Util.rollbackQuietly(connection);
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            Util.rollbackQuietly(connection);
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    public List<User> getAllUsers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                System.out.println(user);
                users.add(user);
            }
            connection.commit();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            Util.rollbackQuietly(connection);
        } finally {
            Util.closeResultSet(resultSet);
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }

        return users;
    }

    public void cleanUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();

            statement.executeUpdate("TRUNCATE users");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }
}
