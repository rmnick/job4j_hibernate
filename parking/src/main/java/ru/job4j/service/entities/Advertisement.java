package ru.job4j.service.entities;

import java.sql.Timestamp;

public class Advertisement {
    private int id;
    private Person person;
    private Timestamp createDate;
    private String description;
    private Car car;
    private String picturePath;
    private boolean sold;

    public Advertisement() {
    }


    public Advertisement(Person person, Timestamp createDate, String description, Car car, String picturePath, boolean sold) {
        this.person = person;
        this.createDate = createDate;
        this.description = description;
        this.car = car;
        this.picturePath = picturePath;
        this.sold = sold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
