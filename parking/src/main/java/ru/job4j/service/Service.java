package ru.job4j.service;

import ru.job4j.persistence.HibernateStore;
import ru.job4j.service.entities.Person;

public class Service implements IService {
    private HibernateStore store = HibernateStore.getInstance();
    private final static Service INSTANCE = new Service();

    public static Service getInstance() {
        return INSTANCE;
    }

    private Service() {

    }

    public boolean validate(Person person) {
        boolean result = false;
        if (store.getPersonByLogin(person) != null) {
            result = true;
        }
        return result;
    }
}
