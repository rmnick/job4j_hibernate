package ru.job4j.service.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Person {
    private int id;
    private String name;
    private String address;
    private String login;
    private String phone;
    private Timestamp dateBirth;
    private List<Car> cars;

    public Person() {
    }

    public Person(final int id,
                  final String name,
                  final String address,
                  final String login,
                  final String phone,
                  final Timestamp dateBirth) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.address = address;
        this.phone = phone;
        this.dateBirth = dateBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id
                && Objects.equals(name, person.name)
                && Objects.equals(address, person.address)
                && Objects.equals(login, person.login)
                && Objects.equals(phone, person.phone)
                && Objects.equals(dateBirth, person.dateBirth)
                && Objects.equals(cars, person.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, login, phone, dateBirth, cars);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Timestamp dateBirth) {
        this.dateBirth = dateBirth;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
