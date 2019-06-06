package ru.job4j.example;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.example.modeles.User;

import java.util.List;
import java.util.function.Function;

public class HibernateStore implements Store<User> {
    private final static Logger LOG = Logger.getLogger(HibernateStore.class.getName());
    private SessionFactory sf;

    public HibernateStore(final SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        T result = null;
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            result = command.apply(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public User add(User item) {
        return tx(session -> {
            session.save(item);
            return item;
        });
    }

    @Override
    public User getById(User item) {
        return tx(session -> {
            return session.get(User.class, item.getId());
        });
    }

    @Override
    public User delete(User item) {
        return tx(session -> {
            session.delete(item);
            return item;
        });
    }

    @Override
    public User update(User item) {
        return tx(session -> {
            session.update(item);
            return item;
        });
    }

    @Override
    public List<User> getAll() {
        return tx(session -> {
            return session.createCriteria(User.class).list();
        });
    }
}
