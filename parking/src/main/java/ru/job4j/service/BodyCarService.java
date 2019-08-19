package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.BodyCarStore;

import ru.job4j.persistence.IBodyCarStore;
import ru.job4j.service.entities.BodyCar;
import ru.job4j.service.entities.Model;

import java.util.List;

public class BodyCarService implements IBodyCarService {
    public static final IBodyCarService INSTANCE = new BodyCarService();
    private static final Logger LOG = Logger.getLogger(BodyCarService.class.getName());
    private IBodyCarStore store;

    private BodyCarService() {
        this.store = BodyCarStore.getInstance();
    }

    public static IBodyCarService getInstance() {
        return INSTANCE;
    }

    @Override
    public BodyCar addEntity(BodyCar bodyCar) {
        return store.addEntity(bodyCar);
    }

    @Override
    public BodyCar updateEntity(BodyCar bodyCar) {
        return store.updateEntity(bodyCar);
    }

    @Override
    public BodyCar deleteEntity(BodyCar bodyCar) {
        return store.deleteEntity(bodyCar);
    }

    /**
     * get all car bodies from DB according to model
     * @param model Model
     * @return List String
     */
    public List<String> getAllBodyCarsByModel(Model model) {
        return store.getAllBodyCarsByModel(model);
    }
}
