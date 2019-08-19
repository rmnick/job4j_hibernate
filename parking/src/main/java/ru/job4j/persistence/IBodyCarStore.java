package ru.job4j.persistence;

import ru.job4j.service.entities.BodyCar;
import ru.job4j.service.entities.Model;

import java.util.List;

public interface IBodyCarStore extends IStore<BodyCar> {
    List<String> getAllBodyCarsByModel(Model model);
}
