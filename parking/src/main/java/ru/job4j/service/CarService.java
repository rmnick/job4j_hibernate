package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.CarStore;
import ru.job4j.persistence.ICarStore;
import ru.job4j.service.entities.Car;

public class CarService implements ICarService {
    private static final Logger LOG = Logger.getLogger(CarService.class.getName());
    public static final ICarService INSTANCE = new CarService();
    private ICarStore store;

    private CarService() {
        this.store = CarStore.getInstance();
    }

    public static ICarService getInstance() {
        return INSTANCE;
    }

    @Override
    public Car addEntity(Car car) {
        return store.addEntity(car);
    }

    @Override
    public Car updateEntity(Car car) {
        return store.updateEntity(car);
    }

    @Override
    public Car deleteEntity(Car car) {
        return store.deleteEntity(car);
    }
}
