package ru.job4j.service.entities;

import java.util.Objects;

public class BodyCar {
    private int id;
    private String name;

    public BodyCar() {
    }

    public BodyCar(final String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BodyCar bodyCar = (BodyCar) o;
        return id == bodyCar.id && Objects.equals(name, bodyCar.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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
