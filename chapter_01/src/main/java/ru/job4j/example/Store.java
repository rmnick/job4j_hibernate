package ru.job4j.example;

import java.util.List;

public interface Store<T> {
    T add(T item);
    T getById(T item);
    T delete(T item);
    T update(T item);
    List<T> getAll();
}
