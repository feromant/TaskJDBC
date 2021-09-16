package jm.task.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/taskjdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1111";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            System.out.println("Не удалось загрузить класс драйвера!");
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }
}
