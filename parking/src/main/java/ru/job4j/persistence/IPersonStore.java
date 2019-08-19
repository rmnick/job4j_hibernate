package ru.job4j.persistence;

import ru.job4j.service.entities.Person;

public interface IPersonStore extends IStore<Person> {
    Person getPersonByLogin(Person person);
    Person getPersonByPhone(Person person);
    Person getPersonByEmail(Person person);

}
