package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.EngineStore;
import ru.job4j.persistence.IEngineStore;
import ru.job4j.service.entities.Engine;
import ru.job4j.service.entities.Model;

import java.util.List;

public class EngineService implements IEngineService {
    public static final IEngineService INSTANCE = new EngineService();
    private static final Logger LOG = Logger.getLogger(EngineService.class.getName());
    private IEngineStore store;

    private EngineService() {
        this.store = EngineStore.getInstance();
    }

    public static IEngineService getInstance() {
        return INSTANCE;
    }

    @Override
    public Engine addEntity(Engine engine) {
        return store.addEntity(engine);
    }

    @Override
    public Engine updateEntity(Engine engine) {
        return store.updateEntity(engine);
    }

    @Override
    public Engine deleteEntity(Engine engine) {
        return store.deleteEntity(engine);
    }

    /**
     * get all engines from DB according to model
     * @param model Model
     * @return List String
     */
    public List<String> getAllEnginesByModel(Model model) {
        return store.getAllEnginesByModel(model);
    }
}
