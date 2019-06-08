package ru.job4j.persistence;

import java.util.List;

public interface IStore<T> {
    T add(T item);
    T update(T item);
    T delete(T item);
    T findById(T item);
    List<T> findAll();

}
