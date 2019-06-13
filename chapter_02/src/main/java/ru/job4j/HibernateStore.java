package ru.job4j;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.entity.Car;
import ru.job4j.entityannotation.CarAn;

import java.util.List;
import java.util.function.Function;

public class HibernateStore {
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

    public Car getCarById(Car item) {
        return tx(session -> {
            return session.get(Car.class, item.getId());
        });
    }

    public CarAn getCarAnById(CarAn item) {
        return tx(session -> {
            return session.get(CarAn.class, item.getId());
        });
    }

    public List<Car> getAllCar() {
        return tx(session -> {
            return session.createCriteria(Car.class).list();
        });
    }

    public List<CarAn> getAllCarAn() {
        return tx(session -> {
            return session.createCriteria(CarAn.class).list();
        });
    }
}
