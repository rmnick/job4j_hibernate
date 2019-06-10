package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.HibernateStore;
import ru.job4j.persistence.IStore;
import ru.job4j.service.entity.Task;

import java.util.List;

public class Service implements IService<Task> {
    private final IStore<Task> store = HibernateStore.getInstance();
    private final static Service INSTANCE = new Service();
    private final static Logger LOG = Logger.getLogger(Service.class.getName());

    private Service() {
    }

    public static IService getInstance() {
        return INSTANCE;
    }

    @Override
    public Task add(Task item) {
        return store.add(item);
    }

    @Override
    public Task delete(Task item) {
        return store.delete(item);
    }

    @Override
    public Task getById(Task item) {
        return store.findById(item);
    }

    @Override
    public Task update(Task item) {
        return store.update(item);
    }

    @Override
    public List<Task> getAll() {
        return store.findAll();
    }
}
