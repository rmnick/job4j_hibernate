package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.function.Function;

public abstract class HibernateStore<V> implements IStore<V> {
    private static final Logger LOG = Logger.getLogger(HibernateStore.class.getName());
    private SessionFactory sf;

    public HibernateStore(final SessionFactory sf) {
        this.sf = sf;
    }

    public <T> T tx(final Function<Session, T> command) {
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

    public <V> V addEntity(V item) {
        return tx(session -> {
            session.save(item);
            return item;
        });
    }

    public <V> V deleteEntity(V item) {
        return tx(session -> {
            session.delete(item);
            return item;
        });
    }

    public <V> V updateEntity(V item) {
        return tx(session -> {
            session.update(item);
            return item;
        });
    }
}
