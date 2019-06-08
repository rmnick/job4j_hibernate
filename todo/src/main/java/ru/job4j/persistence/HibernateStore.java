package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.service.entity.Task;

import java.util.List;
import java.util.function.Function;

public class HibernateStore implements IStore<Task> {
    private final static Logger LOG = Logger.getLogger(HibernateStore.class.getName());
    private final static HibernateStore INSTANCE = new HibernateStore();
    private SessionFactory sf;

    private HibernateStore() {
        sf = SessionFactoryUtil.getSessionFactory();
    }

    public static IStore getInstance() {
        return INSTANCE;
    }

    private <T> T doTransaction(Function<Session, T> function) {
        T result = null;
        Session session = sf.openSession();
        Transaction tr = session.beginTransaction();
        try {
            result = function.apply(session);
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Task add(Task item) {
        return doTransaction(session -> {
            session.save(item);
            return item;
        });
    }

    @Override
    public Task update(Task item) {
        return doTransaction(session -> {
            session.update(item);
            return item;
        });
    }

    @Override
    public Task delete(Task item) {
        return doTransaction(session -> {
            session.delete(item);
            return item;
        });
    }

    @Override
    public Task findById(Task item) {
        return doTransaction(session -> {
            return session.get(Task.class, item.getId());
        });
    }

    @Override
    public List<Task> findAll() {
        return doTransaction(session -> {
            return session.createCriteria(Task.class).list();
        });
    }
}
