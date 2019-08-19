package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateSessionFactoryUtil {
    private static final Logger LOG = Logger.getLogger(HibernateSessionFactoryUtil.class.getName());
    private static SessionFactory sf;

    private HibernateSessionFactoryUtil() {

    }

    public static SessionFactory getFactory() {
        if (sf == null) {
            synchronized (HibernateSessionFactoryUtil.class) {
                try {
                    sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        return sf;
    }
}
