package ru.job4j.service;

import ru.job4j.service.entities.Person;

public interface IPersonService extends IService<Person> {
    boolean validateEnter(Person person);
    Person getPersonByLogin(Person person);
    String validateNotExist(Person person);
    String validateForUpdate(Person oldPerson, Person newPerson);
}
