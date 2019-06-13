package ru.job4j.entity;

public class Engine {
    private int id;
    private String name;

    public Engine() {

    }

    public Engine(String name) {
        this(0, name);
    }

    public Engine(int id, String name) {
        this.id = id;
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
        return String.format("EngineAn{id = %d, name = %s}", this.id, this.name);
    }
}
