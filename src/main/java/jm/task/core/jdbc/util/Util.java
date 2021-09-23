package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/taskjdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1111";

    private static boolean initialized;

    public static void initDriver() {
        if (!initialized) {
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Can't initialize JDBC driver");
            }
            initialized = true;
        }
    }

    public static Connection getConnection() throws SQLException {
        initDriver();
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        conn.setAutoCommit(false);
        return conn;
    }

    public static void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                System.out.println("JDBC Transaction rolled back successfully");
            } catch (SQLException erb) {
                System.out.println("SQLException in rollback" + erb.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
