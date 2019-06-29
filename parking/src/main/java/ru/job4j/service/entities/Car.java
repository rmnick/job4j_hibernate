package ru.job4j.service.entities;

import java.sql.Timestamp;

public class Car {
    private int id;
    private Model model;
    private Timestamp year;
    private int mileage;
    private int price;

    public Car() {
    }

    public Car(final Model model, final Timestamp year, final int mileage, final int price) {
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Timestamp getYear() {
        return year;
    }

    public void setYear(Timestamp year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.model.toString();
    }
}
