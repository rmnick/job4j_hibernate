package ru.job4j.example.modeles;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private Timestamp expired;

    public User() {

    }

    public User(final String name, final Timestamp expired) {
        this.name = name;
        this.expired = expired;
    }

    public User(final int id, final String name, final Timestamp expired) {
        this.id = id;
        this.name = name;
        this.expired = expired;
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

    public Timestamp getExpired() {
        return expired;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return String.format("user id : %d, name: %s", this.id, this.name);
    }
}
