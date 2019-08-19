package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.ITransmissionStore;
import ru.job4j.persistence.TransmissionStore;
import ru.job4j.service.entities.Model;
import ru.job4j.service.entities.Transmission;

import java.util.List;

public class TransmissionService implements ITransmissionService {
    private static final Logger LOG = Logger.getLogger(TransmissionService.class.getName());
    public static final ITransmissionService INSTANCE = new TransmissionService();
    private ITransmissionStore store;

    private TransmissionService() {
        this.store = TransmissionStore.getInstance();
    }

    @Override
    public Transmission addEntity(Transmission transmission) {
        return store.addEntity(transmission);
    }

    @Override
    public Transmission updateEntity(Transmission transmission) {
        return store.updateEntity(transmission);
    }

    @Override
    public Transmission deleteEntity(Transmission transmission) {
        return store.deleteEntity(transmission);
    }

    public static ITransmissionService getInstance() {
        return INSTANCE;
    }

    /**
     * get all transmissions from DB according to model
     * @param model Model
     * @return List String
     */
    public List<String> getAllTransmissionsByModel(Model model) {
        return store.getAllTransmissionsByModel(model);
    }
}
