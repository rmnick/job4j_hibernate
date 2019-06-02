package ru.job4j.example.modeles;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
    private int id;
    private String name;
    private Timestamp expired;

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
}
