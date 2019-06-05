package ru.job4j.example.modeles;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Test;
import ru.job4j.example.HibernateSessionFactoryUtil;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HibernateRunTest {
    private static SessionFactory sf = HibernateSessionFactoryUtil.getFactory();

    @After
    public void close() {
        try {
            sf.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add() throws HibernateException {
//        User user = new User("Nick", new Timestamp(System.currentTimeMillis()));
//        Session session = sf.openSession();
//        session.beginTransaction();
//        session.save(user);
//        User result = session.get(User.class, 1);
//        assertThat(result.getName(), is("Nick"));
//        session.getTransaction().rollback();
//        session.close();
    }
}
