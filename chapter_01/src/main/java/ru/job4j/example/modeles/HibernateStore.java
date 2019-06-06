package ru.job4j.example.modeles;

import org.hibernate.SessionFactory;
import ru.job4j.example.Store;

import java.util.List;

public class HibernateStore implements Store<User> {
    private SessionFactory sf;

    public HibernateStore(final SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public User add(User item) {
        return null;
    }

    @Override
    public User getById(User item) {
        return null;
    }

    @Override
    public User delete(User item) {
        return null;
    }

    @Override
    public User update(User item) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
