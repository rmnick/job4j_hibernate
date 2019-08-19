package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.IModelStore;
import ru.job4j.persistence.ModelStore;
import ru.job4j.service.entities.*;

import java.util.List;

public class ModelService implements IModelService {
    private static final Logger LOG = Logger.getLogger(ModelService.class.getName());
    public static final IModelService INSTANCE = new ModelService();
    private IModelStore store;

    private ModelService() {
        this.store = ModelStore.getInstance();
    }

    public static IModelService getInstance() {
        return INSTANCE;
    }

    @Override
    public Model addEntity(Model model) {
        return store.addEntity(model);
    }

    @Override
    public Model updateEntity(Model model) {
        return store.updateEntity(model);
    }

    @Override
    public Model deleteEntity(Model model) {
        return store.deleteEntity(model);
    }

    /**
     * get all models names according to brand
     * @param brand Brand
     * @return List String
     */
    public List<String> getAllModelsNamesByBrand(Brand brand) {
        return store.getAllModelsNamesByBrand(brand);
    }

    /**
     * get all models from DB
     * @return List models
     */
    public List<Model> getAllModels() {
        return store.getAllModels();
    }

    /**
     * get model from DB according to parameters
     * @param model Model
     * @param engine Engine
     * @param transmission Transmission
     * @param bodyCar BodyCar
     * @return Model model
     */
    public Model getModelByParam(Model model, Engine engine, Transmission transmission, BodyCar bodyCar) {
        return store.getModelByParam(model, engine, transmission, bodyCar);
    }
}
