package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.IPersonStore;
import ru.job4j.persistence.PersonStore;
import ru.job4j.service.entities.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PersonService implements IPersonService {
    private static final Logger LOG = Logger.getLogger(PersonService.class.getName());
    public static final IPersonService INSTANCE = new PersonService();
    private IPersonStore store;
    public static final String LOGIN = "login";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public final Map<String, Function<Person, Person>> validationMap = new HashMap<>();

    /**
     * init the map to check the input fields to exist
     */
    private void initValidationMap() {
        validationMap.put(LOGIN, checkLogin());
        validationMap.put(PHONE, checkPhone());
        validationMap.put(EMAIL, checkEmail());
    }

    private PersonService() {
        this.store = PersonStore.getInstance();
        initValidationMap();
    }

    public static IPersonService getInstance() {
        return INSTANCE;
    }

    @Override
    public Person addEntity(Person person) {
        return store.addEntity(person);
    }

    @Override
    public Person updateEntity(Person person) {
        return store.updateEntity(person);
    }

    @Override
    public Person deleteEntity(Person person) {
        return store.deleteEntity(person);
    }

    /**
     * user data validation for existing
     * @param person Person
     * @return String answer
     */
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

    /**
     * validating data from updating page
     * @param oldPerson Person
     * @param newPerson Person
     * @return answer String
     */
    public String validateForUpdate(Person oldPerson, Person newPerson) {
        Person checkPerson = new Person();
        if (!oldPerson.getEmail().equals(newPerson.getEmail())) {
            checkPerson.setEmail(newPerson.getEmail());
        }
        if (!oldPerson.getPhone().equals(newPerson.getPhone())) {
            checkPerson.setPhone(newPerson.getPhone());
        }
        return validateNotExist(checkPerson);
    }

    /**
     * data validation for user sign-in
     * @param person Person
     * @return boolean
     */
    public boolean validateEnter(Person person) {
        boolean result = false;
        Person personFromDb = store.getPersonByLogin(person);
        if  (personFromDb != null && person.getPassword().equals(personFromDb.getPassword())) {
            result = true;
        }
        return result;
    }

    /**
     * get person from DB by person login
     * @param person Person
     * @return person Person
     */
    public Person getPersonByLogin(Person person) {
        return store.getPersonByLogin(person);
    }

    /**
     * get person from DB by login
     * @return person Person
     */
    private Function<Person, Person> checkLogin() {
        return person -> {
            return store.getPersonByLogin(person);
        };
    }

    /**
     * get person from DB by phone
     * @return person Person
     */
    private Function<Person, Person> checkPhone() {
        return person -> {
            return store.getPersonByPhone(person);
        };
    }

    /**
     * get person from DB by email
     * @return person Person
     */
    private Function<Person, Person> checkEmail() {
        return person -> {
            return store.getPersonByEmail(person);
        };
    }
}
