package ru.job4j.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.example.modeles.User;

import java.time.LocalDateTime;

public class HibernateRun {
    public static void main(String[] args) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        User user = new User();
        user.setName("Nick");
//        user.setExpired(LocalDateTime.now());
        session.save(user);
        session.getTransaction().commit();
        session.close();
        sf.close();
    }
}
