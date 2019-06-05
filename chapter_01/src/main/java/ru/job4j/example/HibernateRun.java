package ru.job4j.example;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import ru.job4j.example.modeles.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HibernateRun implements AutoCloseable {
    private static SessionFactory sessionFactory = HibernateSessionFactoryUtil.getFactory();

    private final static Logger LOG = Logger.getLogger(HibernateRun.class.getName());

    public static void main(String[] args) {
        HibernateRun hr = new HibernateRun();
        User user = new User("Nick", new Timestamp(System.currentTimeMillis()));

        hr.add(user);
        user = hr.getUserById(new User(1, null, null));
        System.out.println(user);
        user.setName("Fedya");

        hr.update(user);
        user = hr.getUserById(new User(1, null, null));
        System.out.println(user);

        hr.add(new User("Alex", new Timestamp(System.currentTimeMillis())));
        hr.delete(new User(1, null, null));
        hr.getAll().forEach(System.out :: println);
        HibernateRun.sessionFactory.close();
    }

    public void add(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            } catch (HibernateException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public User getUserById(User user) {
        User result = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = session.get(User.class, user.getId());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    public void update(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            } catch (HibernateException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public void delete(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            } catch (HibernateException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = session.createCriteria(User.class).addOrder(Order.desc("id")).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void close() {
        try {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
