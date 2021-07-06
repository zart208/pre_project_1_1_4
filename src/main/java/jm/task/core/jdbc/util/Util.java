package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public final static String URL = "jdbc:mysql://localhost:3306/test";
    public final static String USER = "root";
    public final static String PASSWORD = "Lthgfhjkm_2014";

    public static Connection getConnection() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
}
