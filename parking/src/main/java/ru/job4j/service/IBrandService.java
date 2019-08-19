package ru.job4j.service;

import ru.job4j.service.entities.Brand;

import java.util.List;

public interface IBrandService extends IService<Brand> {
    List<Brand> getAllBrands();
}
