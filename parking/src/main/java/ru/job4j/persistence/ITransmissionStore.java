package ru.job4j.persistence;

import ru.job4j.service.entities.Model;
import ru.job4j.service.entities.Transmission;

import java.util.List;

public interface ITransmissionStore extends IStore<Transmission> {
    List<String> getAllTransmissionsByModel(Model model);
}
