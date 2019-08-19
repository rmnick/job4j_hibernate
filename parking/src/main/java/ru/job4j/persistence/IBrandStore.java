package ru.job4j.persistence;

import ru.job4j.service.entities.Brand;

import java.util.List;

public interface IBrandStore extends IStore<Brand> {
    List<Brand> getAllBrands();
}
