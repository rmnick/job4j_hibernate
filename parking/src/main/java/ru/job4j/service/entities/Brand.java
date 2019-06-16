package ru.job4j.service.entities;

public class Brand {
    private int id;
    private String name;

    public Brand() {
    }

    public Brand(final String name) {
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
}
