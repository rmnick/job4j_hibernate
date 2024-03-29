package ru.job4j.service.entities;


public class Engine {
    private int id;
    private String name;

    public Engine() {
    }

    public Engine(final String name) {
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

    @Override
    public String toString() {
        return String.format("Engine{name:%s}", this.getName());
    }
}
