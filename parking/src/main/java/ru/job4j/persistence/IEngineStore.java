package ru.job4j.persistence;

import ru.job4j.service.entities.Engine;
import ru.job4j.service.entities.Model;

import java.util.List;

public interface IEngineStore extends IStore<Engine> {
    List<String> getAllEnginesByModel(Model model);
}
