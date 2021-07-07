package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public final static String URL = "jdbc:mysql://localhost:3306/test";
    public final static String USER = "root";
    public final static String PASSWORD = "Lthgfhjkm_2014";

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry standardServiceRegistry;

    static {
        Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.setProperty(Environment.USER, "root");
        properties.setProperty(Environment.PASS, "Lthgfhjkm_2014");
        properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/test");

        standardServiceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .build();
        MetadataSources sources = new MetadataSources(standardServiceRegistry)
                .addAnnotatedClass(User.class);
        try {
            sessionFactory = sources.buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            if (standardServiceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection getConnection() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
}