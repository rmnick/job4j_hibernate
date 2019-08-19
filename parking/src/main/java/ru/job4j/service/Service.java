package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.*;

public abstract class Service<V> implements IService<V> {
    public static final Logger LOG = Logger.getLogger(Service.class.getName());
    public IStore store;

    public Service(final IStore store) {
        this.store = store;
    }

    public  V addEntity(V entity) {
        return (V) store.addEntity(entity);
    }

    public V updateEntity(V entity) {
        return (V) store.updateEntity(entity);
    }

    public V deleteEntity(V entity) {
        return (V) store.deleteEntity(entity);
    }
}
