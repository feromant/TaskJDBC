package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {

    public static void main(String[] args) {
        UserDao dao = new UserDaoJDBCImpl();
        dao.createUsersTable();
        dao.saveUser("Рулон", "Обоев", (byte) 32);
        dao.saveUser("Ушат", "Помоев", (byte) 20);
        dao.saveUser("Улов", "Налимов", (byte) 63);
        dao.saveUser("Букет", "Левкоев", (byte) 45);
        dao.getAllUsers();
        dao.cleanUsersTable();
        dao.dropUsersTable();
    }
}
