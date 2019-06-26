package ru.job4j.service.entities;

import org.hibernate.annotations.Proxy;

public class Model {
    private int id;
    private String name;
    private Engine engine;
    private BodyCar bodyCar;
    private Transmission transmission;
    private Brand brand;

    public Model() {
    }

    public Model(final String name) {
        this.name = name;
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return String.format("Model{name:%s}", this.getName());
    }
}
