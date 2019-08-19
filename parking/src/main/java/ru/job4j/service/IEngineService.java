package ru.job4j.service;

import ru.job4j.service.entities.Engine;
import ru.job4j.service.entities.Model;

import java.util.List;

public interface IEngineService extends IService<Engine> {
    List<String> getAllEnginesByModel(Model model);
}
