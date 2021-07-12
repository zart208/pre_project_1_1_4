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
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `test`.`users` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` SMALLINT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8\n" +
                    "COLLATE = utf8_unicode_ci;");
            tryCommit(connection);
        } catch (SQLException e) {
            System.out.println("Error on create table query: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP  TABLE IF EXISTS users ;");
            tryCommit(connection);
        } catch (SQLException e) {
            System.out.println("Error on drop table query!!!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES((?), (?), (?))")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            tryCommit(connection);
            System.out.println("User с именем " + name + " " + lastName + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Error on insert query!!!");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = (?)")) {
            statement.setLong(1, id);
            statement.executeUpdate();
            tryCommit(connection);
        } catch (SQLException e) {
            System.out.println("Error on remove query!!!");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User tmpUser = new User();
                tmpUser.setId(resultSet.getLong("id"));
                tmpUser.setName(resultSet.getString("name"));
                tmpUser.setLastName(resultSet.getString("lastname"));
                tmpUser.setAge(resultSet.getByte("age"));
                users.add(tmpUser);
            }
            tryCommit(connection);
        } catch (SQLException e) {
            System.out.println("Error on get all users query!!!");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE users;");
            tryCommit(connection);
        } catch (SQLException e) {
            System.out.println("Error on clean table query!!!");
        }
    }

    private void tryCommit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении транзакции!!! Попытка откатить транзакцию");
            try {
                connection.rollback();
                System.out.println("Откат транзакции произведен успешно!!!");
            } catch (SQLException exception) {
                System.out.println("Ошибка при попытке отката транзакции!!!");
            }
        }
    }
}