package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import ru.job4j.service.entities.Person;

public class PersonStore extends HibernateStore<Person> implements IPersonStore {
    private static final IPersonStore INSTANCE = new PersonStore(HibernateSessionFactoryUtil.getFactory());
    private static final Logger LOG = Logger.getLogger(PersonStore.class.getName());

    private PersonStore(SessionFactory sf) {
        super(sf);
    }

    public static IPersonStore getInstance() {
        return INSTANCE;
    }

    /**
     * get person from DB by login
     * @return person Person
     */
    public Person getPersonByLogin(Person person) {
        return tx(session -> {
            Person result;
            Query query = session.createQuery("from Person where login=:login");
            query.setParameter("login", person.getLogin());
            result = (Person) query.uniqueResult();
            return result;
        });
    }

    /**
     * get person from DB by phone
     * @return person Person
     */
    public Person getPersonByPhone(Person person) {
        return tx(session -> {
            Person result;
            Query query = session.createQuery("from Person where phone=:phone");
            query.setParameter("phone", person.getPhone());
            result = (Person) query.uniqueResult();
            return result;
        });
    }

    /**
     * get person from DB by email
     * @return person Person
     */
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
