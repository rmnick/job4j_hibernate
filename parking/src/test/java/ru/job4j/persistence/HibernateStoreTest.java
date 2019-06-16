package ru.job4j.persistence;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.junit.Test;
import ru.job4j.service.entities.*;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class HibernateStoreTest {

    @Test
    public void addAndGet() throws HibernateException {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        HibernateStore store = new HibernateStore(factory);
        Engine engine = new Engine("3.5 turbo");
        BodyCar bodyCar = new BodyCar("hatchback");
        Transmission transmission = new Transmission("all-road");
        Person person = new Person();
        Car car = new Car(0, "Audi", new Timestamp(System.currentTimeMillis()), engine, bodyCar, transmission, person);
        store.addEntity(engine);
        store.addEntity(bodyCar);
        store.addEntity(transmission);
        store.addEntity(person);
        store.addEntity(car);
        assertThat(
                store.getCarById(car).getEngine().getName(),
                is("3.5 turbo")
        );
        factory.close();
    }

    @Test
    public void delete() {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        HibernateStore store = new HibernateStore(factory);
        Engine engine = new Engine("3.5 turbo");
        BodyCar bodyCar = new BodyCar("hatchback");
        Transmission transmission = new Transmission("all-road");
        Person person = new Person(1, "Ivan", "Magnitogorsk", "vanya", "9068510875", new Timestamp(System.currentTimeMillis()));
        Car audi = new Car(1, "Audi", new Timestamp(System.currentTimeMillis()), engine, bodyCar, transmission, person);
        Car bmw = new Car(2, "BMW", new Timestamp(System.currentTimeMillis()), engine, bodyCar, transmission, person);
        store.addEntity(engine);
        store.addEntity(bodyCar);
        store.addEntity(transmission);
        store.addEntity(person);
        store.addEntity(audi);
        store.addEntity(bmw);
        store.deleteEntity(audi);
        assertThat(
                store.getAllCar().size(),
                is(1)
        );
        factory.close();
    }

    @Test
    public void update() {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        HibernateStore store = new HibernateStore(factory);
        Engine engineOne = new Engine("3.5 turbo");
        Engine engineTwo = new Engine("2.5 tdi");
        BodyCar bodyCar = new BodyCar("hatchback");
        Transmission transmission = new Transmission("all-road");
        Person person = new Person(1, "Ivan", "Magnitogorsk", "vanya", "9068510875", new Timestamp(System.currentTimeMillis()));
        Car car = new Car(0, "Audi", new Timestamp(System.currentTimeMillis()), engineOne, bodyCar, transmission, person);
        store.addEntity(engineOne);
        store.addEntity(engineTwo);
        store.addEntity(bodyCar);
        store.addEntity(transmission);
        store.addEntity(person);
        store.addEntity(car);
        car.setEngine(engineTwo);
        store.updateEntity(car);
        assertThat(
                store.getCarById(car).getEngine().getName(),
                is("2.5 tdi")
        );
        factory.close();
    }
}
