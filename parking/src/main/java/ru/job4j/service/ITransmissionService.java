package ru.job4j.service;

import ru.job4j.service.entities.Model;
import ru.job4j.service.entities.Transmission;

import java.util.List;

public interface ITransmissionService extends IService<Transmission> {
    List<String> getAllTransmissionsByModel(Model model);
}
