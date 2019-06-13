package ru.job4j.entity;

public class BodyCar {
    private int id;
    private String name;

    public BodyCar() {

    }

    public BodyCar(final int id) {
        this(id, null);
    }

    public BodyCar(final String name) {
        this(0, name);
    }

    public BodyCar(final int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("BodyCarAn{id = %d, name = %s}", this.id, this.name);
    }
}
