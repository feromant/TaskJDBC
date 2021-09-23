package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static long userCount = 1;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT UNIQUE AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(10), " +
                    "lastname VARCHAR(10), " +
                    "age TINYINT)");
            connection.commit();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            Util.rollbackQuietly(connection);
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
            connection.commit();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            Util.rollbackQuietly(connection);
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
                    "INSERT INTO users (id, name, lastname, age) VALUES(?, ?, ?, ?)");

            statement.setLong(1, userCount);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setByte(4, age);

            statement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
            userCount++;
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
            connection.commit();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            Util.rollbackQuietly(connection);
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }
}
