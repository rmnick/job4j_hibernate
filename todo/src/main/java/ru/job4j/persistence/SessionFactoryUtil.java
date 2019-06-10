package ru.job4j.persistence;


import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {
    public static final Logger LOG = Logger.getLogger(SessionFactory.class.getName());
    private static SessionFactory sf;

    private SessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sf == null) {
            try {
                sf = new Configuration().configure().buildSessionFactory();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return sf;
    }
}
