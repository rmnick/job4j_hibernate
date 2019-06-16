package ru.job4j.service.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class Car {
    private int id;
    private String name;
    private Timestamp yearOfManufact;
    private Engine engine;
    private BodyCar bodyCar;
    private Transmission transmission;
    private Person person;

    public Car() {
    }

    public Car(final int id,
               final String name,
               final Timestamp yearOfManufact,
               final Engine engine,
               final BodyCar bodyCar,
               final Transmission transmission,
               final Person person) {
        this.id = id;
        this.name = name;
        this.bodyCar = bodyCar;
        this.engine = engine;
        this.transmission = transmission;
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id
                && Objects.equals(name, car.name)
                && Objects.equals(yearOfManufact, car.yearOfManufact)
                && Objects.equals(engine, car.engine)
                && Objects.equals(bodyCar, car.bodyCar)
                && Objects.equals(transmission, car.transmission)
                && Objects.equals(person, car.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, yearOfManufact, engine, bodyCar, transmission, person);
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

    public Timestamp getYearOfManufact() {
        return yearOfManufact;
    }

    public void setYearOfManufact(Timestamp yearOfManufact) {
        this.yearOfManufact = yearOfManufact;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public BodyCar getBodyCar() {
        return bodyCar;
    }

    public void setBodyCar(BodyCar bodyCar) {
        this.bodyCar = bodyCar;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
