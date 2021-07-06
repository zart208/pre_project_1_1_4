package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        User ivanov = new User("Ivan", "Ivanov", (byte)43);
        User petrov = new User("Petr", "Petrov", (byte)35);
        User sidorov = new User("Sidor", "Sidorov", (byte)28);
        User aleksandrov = new User("aleksandr", "aleksandrov", (byte)51);
        userService.saveUser(ivanov.getName(), ivanov.getLastName(), ivanov.getAge());
        userService.saveUser(petrov.getName(), petrov.getLastName(), petrov.getAge());
        userService.saveUser(sidorov.getName(), sidorov.getLastName(), sidorov.getAge());
        userService.saveUser(aleksandrov.getName(), aleksandrov.getLastName(), aleksandrov.getAge());

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
