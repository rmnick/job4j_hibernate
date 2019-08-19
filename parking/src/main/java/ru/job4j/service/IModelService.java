package ru.job4j.service;

import ru.job4j.service.entities.*;

import java.util.List;

public interface IModelService extends IService<Model> {
    List<String> getAllModelsNamesByBrand(Brand brand);
    List<Model> getAllModels();
    Model getModelByParam(Model model, Engine engine, Transmission transmission, BodyCar bodyCar);
}
