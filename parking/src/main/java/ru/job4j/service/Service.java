package ru.job4j.service;

import ru.job4j.persistence.HibernateStore;
import ru.job4j.service.entities.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Service implements IService {
    private final HibernateStore store = HibernateStore.getInstance();
    private final static Service INSTANCE = new Service();
    public final static String LOGIN = "login";
    public final static String PHONE = "phone";
    public final static String EMAIL = "email";
    public final static String SUCCESS = "success";
    private final Map<String, Function<Person, Person>> validationMap = new HashMap<>();

    public static Service getInstance() {
        return INSTANCE;
    }

    private void initValidationMap() {
        validationMap.put(LOGIN, checkLogin());
        validationMap.put(PHONE, checkPhone());
        validationMap.put(EMAIL, checkEmail());
    }

    private Service() {
        initValidationMap();
    }

    public <V> V addEntity(V entity) {
        return store.addEntity(entity);
    }

    public String validateNotExist(Person person) {
        String result = SUCCESS;
        for (String str : validationMap.keySet()) {
            if (validationMap.get(str).apply(person) != null) {
                result = str;
                break;
            }
        }
        return result;
    }

    public boolean validateEnter(Person person) {
        boolean result = false;
        Person personFromDb = store.getPersonByLogin(person);
        if  (personFromDb != null && person.getPassword().equals(personFromDb.getPassword())) {
            result = true;
        }
        return result;
    }

    private Function<Person, Person> checkLogin() {
        return person -> {
            return store.getPersonByLogin(person);
        };
    }

    private Function<Person, Person> checkPhone() {
        return person -> {
            return store.getPersonByPhone(person);
        };
    }

    private Function<Person, Person> checkEmail() {
        return person -> {
            return store.getPersonByEmail(person);
        };
    }
}
