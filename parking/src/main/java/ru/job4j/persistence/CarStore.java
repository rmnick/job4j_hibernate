package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import ru.job4j.service.entities.Car;

public class CarStore extends HibernateStore<Car> implements ICarStore {
    private static final ICarStore INSTANCE = new CarStore(HibernateSessionFactoryUtil.getFactory());
    private static final Logger LOG = Logger.getLogger(CarStore.class.getName());

    public CarStore(final SessionFactory sf) {
        super(sf);
    }

    public static ICarStore getInstance() {
        return INSTANCE;
    }
}
