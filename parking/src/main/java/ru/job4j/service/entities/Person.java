package ru.job4j.service.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Person {
    private int id;
    private String name;
    private String email;
    private String login;
    private String phone;
    private String password;
    private List<Advertisement> ads;

    public Person() {
    }

    public Person(final String login, final String password) {
        this(0, null, null, login, null, password, null);
    }

    public Person(final int id,
                  final String name,
                  final String email,
                  final String login,
                  final String phone,
                  final String password,
                  final List<Advertisement> ads) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.ads = ads;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Advertisement> getAds() {
        return ads;
    }

    public void setAds(List<Advertisement> ads) {
        this.ads = ads;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
