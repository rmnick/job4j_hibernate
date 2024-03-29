package ru.job4j.service.entities;

import java.util.List;

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

    public Person(final String name, final String login, final String password, final String email, final String phone) {
        this(0, name, email, login, phone, password, null);
    }

    public Person(final String login) {
        this.login = login;
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

    @Override
    public String toString() {
        return String.format("Person{name : %s, login : %s, password : %s, email : %s, phone : %s}",
                this.name, this.login, this.password, this.email, this.phone);
    }
}
