package ru.job4j.example;

import org.hibernate.*;
import org.junit.AfterClass;
import org.junit.Test;
import ru.job4j.example.modeles.User;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class HibernateStoreTest {

    @Test
    public void add() throws HibernateException {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        HibernateStore store = new HibernateStore(factory);
        User user = store.add(new User(1, "Petr Arsentev", new Timestamp(System.currentTimeMillis())));
        assertThat(
                store.getById(user).getName(),
                is("Petr Arsentev"));
        factory.close();
    }

    @Test
    public void get() {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        Session session = factory.openSession();
        HibernateStore store = new HibernateStore(factory);
        User userOne = store.add(new User(1, "Petr Arsentev", new Timestamp(System.currentTimeMillis())));
        User userTwo = store.add(new User(2, "Nick Rodionov", new Timestamp(System.currentTimeMillis())));
        assertThat(
                store.getById(userTwo).getName(),
                is("Nick Rodionov")
        );
        session.clear();
        factory.close();
    }

    @Test
    public void delete() {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        Session session = factory.openSession();
        HibernateStore store = new HibernateStore(factory);
        User userOne = store.add(new User(1, "Petr Arsentev", new Timestamp(System.currentTimeMillis())));
        User userTwo = store.add(new User(2, "Nick Rodionov", new Timestamp(System.currentTimeMillis())));
        store.delete(userOne);
        assertThat(
                store.getAll().get(0).getName(),
                is("Nick Rodionov")
        );
        session.clear();
        factory.close();
    }

    @Test
    public void update() {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        Session session = factory.openSession();
        HibernateStore store = new HibernateStore(factory);
        User user = store.add(new User(1, "Petr Arsentev", new Timestamp(System.currentTimeMillis())));
        user.setName("Nick Rodionov");
        store.update(user);
        assertThat(
                store.getById(user).getName(),
                is("Nick Rodionov")
        );
        session.clear();
        factory.close();
    }

    /**
     * Clean tests data.
     */
    @AfterClass
    public static void close() {
        HibernateSessionFactoryUtil.getFactory().close();
    }
}
