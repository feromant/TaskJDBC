package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Рулон", "Обоев", (byte) 32);
        service.saveUser("Ушат", "Помоев", (byte) 20);
        service.saveUser("Улов", "Налимов", (byte) 63);
        service.saveUser("Букет", "Левкоев", (byte) 45);
        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
