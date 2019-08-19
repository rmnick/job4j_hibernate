package ru.job4j.service;


public interface IService<V> {
    String SUCCESS = "success";
    V addEntity(V entity);
    V updateEntity(V entity);
    V deleteEntity(V entity);
}