package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.service.entities.Brand;
import ru.job4j.service.entities.Car;
import ru.job4j.service.entities.Model;
import ru.job4j.service.entities.Person;

import java.util.List;
import java.util.function.Function;

public class HibernateStore {
    private final static HibernateStore INSTANCE = new HibernateStore(HibernateSessionFactoryUtil.getFactory());
    private final static Logger LOG = Logger.getLogger(HibernateStore.class.getName());
    private SessionFactory sf;

    private HibernateStore(final SessionFactory sf) {
        this.sf = sf;
    }

    public static HibernateStore getInstance() {
        return INSTANCE;
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

    public List<Car> getAllCar() {
        return tx(session -> {
            return session.createCriteria(Car.class).list();
        });
    }

    public List<Brand> getAllBrands() {
        return tx(session -> {
            return session.createCriteria(Brand.class).list();
        });
    }

    public List<String> getAllModelsNamesByBrand(Brand brand) {
        return tx(session -> {
            List<String> result;
            Query query = session.createQuery("select model.name from Model as model inner join model.brand as br with br.name=:name");
            query.setParameter("name", brand.getName());
            result = query.list();
            return result;
        });
    }

    public List<Model> getAllModels() {
        return tx(session -> {
            return session.createCriteria(Model.class).list();
        });
    }

    public Person getPersonByLogin(Person person) {
        return tx(session -> {
           Person result;
           Query query = session.createQuery("from Person where login=:login");
           query.setParameter("login", person.getLogin());
           result = (Person) query.uniqueResult();
           return result;
        });
    }

    public Person getPersonByPhone(Person person) {
        return tx(session -> {
            Person result;
            Query query = session.createQuery("from Person where phone=:phone");
            query.setParameter("phone", person.getPhone());
            result = (Person) query.uniqueResult();
            return result;
        });
    }

    public Person getPersonByEmail(Person person) {
        return tx(session -> {
            Person result;
            Query query = session.createQuery("from Person where email=:email");
            query.setParameter("email", person.getEmail());
            result = (Person) query.uniqueResult();
            return result;
        });
    }
}
