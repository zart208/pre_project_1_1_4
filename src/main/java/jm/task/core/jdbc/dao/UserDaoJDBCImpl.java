package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `test`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8\n" +
                    "COLLATE = utf8_unicode_ci;");
        } catch (SQLException e) {
            System.out.println("Error on create table query!!!");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP  TABLE IF EXISTS users ;");
        } catch (SQLException e) {
            System.out.println("Error on drop table query!!!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO users (name, lastname, age) VALUES('");
            query.append(name);
            query.append("', '");
            query.append(lastName);
            query.append("', ");
            query.append(age);
            query.append(");");
            statement.executeUpdate(query.toString());
            System.out.println("User с именем " + name + " " + lastName + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Error on insert query!!!");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM users WHERE id = ");
            query.append(id);
            query.append(";");
            statement.executeUpdate(query.toString());
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
        } catch (SQLException e) {
            System.out.println("Error on get all users query!!!");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM users;");
        } catch (SQLException e) {
            System.out.println("Error on clean table query!!!");
        }
    }
}
