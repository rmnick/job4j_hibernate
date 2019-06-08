package ru.job4j.service;

import java.util.List;

public interface IService<T> {
    T add(T item);
    T delete(T item);
    T getById(T item);
    T update(T item);
    List<T> getAll(T item);
}
