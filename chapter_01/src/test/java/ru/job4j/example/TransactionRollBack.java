package ru.job4j.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Proxy;

public class TransactionRollBack {
    /**
     * Proxy for session factory.
     * @param factory session factory.
     * @return session factory.
     */
    public static SessionFactory create(SessionFactory factory) {
        Session session = factory.openSession();
        session.beginTransaction();
        return (SessionFactory) Proxy.newProxyInstance(
                TransactionRollBack.class.getClassLoader(),
                new Class[] {
                        SessionFactory.class
                },
                (proxy, method, args) -> {
                    Object rsl = null;
                    if ("openSession".equals(method.getName())) {
                        rsl = create(session);
                    } else if ("close".equals(method.getName())) {
                        session.getTransaction().rollback();
                        session.close();
                    } else {
                        rsl = method.invoke(factory, args);
                    }
                    return rsl;
                }
        );
    }

    /**
     * Proxy for session.
     * @param session session.
     * @return session.
     */
    public static Session create(Session session) {
        return (Session) Proxy.newProxyInstance(
                TransactionRollBack.class.getClassLoader(),
                new Class[] {
                        Session.class
                },
                (proxy, method, args) -> {
                    Object rsl;
                    if ("beginTransaction".equals(method.getName())) {
                        rsl = mock(Transaction.class);
                    } else if ("close".equals(method.getName())) {
                        rsl = null;
                    } else if ("clear".equals(method.getName())) {
                        session.getTransaction().rollback();
                        rsl = null;
                    } else {
                        rsl = method.invoke(session, args);
                    }
                    return rsl;
                }
        );
    }
}
