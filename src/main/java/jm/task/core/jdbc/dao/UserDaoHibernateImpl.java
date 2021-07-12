package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (final Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS `test`.`users` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` TINYINT NULL,\n" +
                    "  PRIMARY KEY (`id`))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8\n" +
                    "COLLATE = utf8_unicode_ci;").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Opening session error!!! " + e.getMessage());
        } catch (IllegalStateException | RollbackException exception) {
            System.out.println("Commit error!!! Try to rollback transaction...");
            try {
                transaction.rollback();
            } catch (IllegalStateException | PersistenceException ex) {
                System.out.println("Transaction rollback error!!!");
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (final Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP  TABLE IF EXISTS users ;").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Opening session error!!! " + e.getMessage());
        } catch (IllegalStateException | RollbackException exception) {
            System.out.println("Commit error!!! Try to rollback transaction...");
            try {
                transaction.rollback();
            } catch (IllegalStateException | PersistenceException ex) {
                System.out.println("Rollback error!!!");
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (final Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Opening session error!!! " + e.getMessage());
        } catch (IllegalStateException | RollbackException exception) {
            System.out.println("Commit error!!! Try to rollback transaction...");
            try {
                transaction.rollback();
            } catch (IllegalStateException | PersistenceException ex) {
                System.out.println("Rollback error!!!");
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (final Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User tmpUser = session.get(User.class, id);
            if (tmpUser != null) {
                session.delete(tmpUser);
            } else {
                System.out.println("User wth ID = " + id + " not found!!!");
            }
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Error!!! " + e.getMessage());
        } catch (IllegalStateException | RollbackException exception) {
            System.out.println("Commit error!!! Try to rollback transaction...");
            try {
                transaction.rollback();
            } catch (IllegalStateException | PersistenceException ex) {
                System.out.println("Rollback error!!!");
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;
        try (final Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User");
            users = query.getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Opening session error!!! " + e.getMessage());
        } catch (IllegalStateException | RollbackException exception) {
            System.out.println("Commit error!!! Try to rollback transaction...");
            try {
                transaction.rollback();
            } catch (IllegalStateException | PersistenceException ex) {
                System.out.println("Rollback error!!!");
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (final Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users;").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Opening session error!!! " + e.getMessage());
        } catch (IllegalStateException | RollbackException exception) {
            System.out.println("Commit error!!! Try to rollback transaction...");
            try {
                transaction.rollback();
            } catch (IllegalStateException | PersistenceException ex) {
                System.out.println("Rollback error!!!");
            }
        }
    }
}