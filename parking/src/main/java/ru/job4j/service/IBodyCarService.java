package ru.job4j.service;

import ru.job4j.service.entities.BodyCar;
import ru.job4j.service.entities.Model;

import java.util.List;

public interface IBodyCarService extends IService<BodyCar> {
    List<String> getAllBodyCarsByModel(Model model);
}
