package ru.job4j.entity;

import java.sql.Timestamp;

public class Car {
    private int id;
    private String name;
    private Timestamp yearOfMan;
    private BodyCar bodyCar;
    private Engine engine;
    private Transmission transmission;


    public Car() {

    }

    public Car(final int id) {
        this(id, null, null, null, null, null);
    }

    public Car(final int id, final String name, final Timestamp yearOfMan, final BodyCar bodyCar, final Engine engine, final Transmission transmission) {
        this.id = id;
        this.name = name;
        this.yearOfMan = yearOfMan;
        this.bodyCar = bodyCar;
        this.engine = engine;
        this.transmission = transmission;
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

    public BodyCar getBodyCar() {
        return bodyCar;
    }

    public void setBodyCar(BodyCar bodyCar) {
        this.bodyCar = bodyCar;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Timestamp getYearOfMan() {
        return yearOfMan;
    }

    public void setYearOfMan(Timestamp yearOfMan) {
        this.yearOfMan = yearOfMan;
    }

    @Override
    public String toString() {
        return String.format("CarAn{id = %d, name = %s, yearOfMan = %s, engine = %s, bodyCar = %s, transmission = %s}",
                this.id,
                this.name,
                this.yearOfMan.toString(),
                this.engine != null ? this.engine.getName() : "null",
                this.bodyCar != null ? this.bodyCar.getName() : "null",
                this.transmission != null ? this.transmission.getName() : "null"
        );
    }
}
