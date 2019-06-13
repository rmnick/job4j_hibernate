package ru.job4j.annotationdesign;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Test;
import ru.job4j.HibernateSessionFactoryUtil;
import ru.job4j.HibernateStore;
import ru.job4j.entityannotation.BodyCarAn;
import ru.job4j.entityannotation.CarAn;
import ru.job4j.entityannotation.EngineAn;
import ru.job4j.entityannotation.TransmissionAn;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class HibernateStoreTest {

    @Test
    public void add() {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        HibernateStore store = new HibernateStore(factory);
        EngineAn engine = new EngineAn("3.5 turbo");
        BodyCarAn bodyCar = new BodyCarAn("hatchback");
        TransmissionAn transmission = new TransmissionAn("all-road");
        CarAn audi = new CarAn(1, "Audi", new Timestamp(System.currentTimeMillis()), bodyCar, engine, transmission);
        CarAn bmw = new CarAn(2, "BMW", new Timestamp(System.currentTimeMillis()), bodyCar, engine, transmission);
        store.addEntity(engine);
        store.addEntity(bodyCar);
        store.addEntity(transmission);
        store.addEntity(audi);
        store.addEntity(bmw);
        assertThat(
                store.getAllCarAn().size(),
                is(2)
        );
        factory.close();
    }

    @Test
    public void addAndGet() throws HibernateException {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        HibernateStore store = new HibernateStore(factory);
        EngineAn engine = new EngineAn("3.5 turbo");
        BodyCarAn bodyCar = new BodyCarAn("hatchback");
        TransmissionAn transmission = new TransmissionAn("all-road");
        CarAn car = new CarAn(0, "Audi", new Timestamp(System.currentTimeMillis()), bodyCar, engine, transmission);
        store.addEntity(engine);
        store.addEntity(bodyCar);
        store.addEntity(transmission);
        store.addEntity(car);
        assertThat(
                store.getCarAnById(car).getEngineAn().getName(),
                is("3.5 turbo")
        );
        factory.close();
    }

    @Test
    public void delete() {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        HibernateStore store = new HibernateStore(factory);
        EngineAn engine = new EngineAn("3.5 turbo");
        BodyCarAn bodyCar = new BodyCarAn("hatchback");
        TransmissionAn transmission = new TransmissionAn("all-road");
        CarAn audi = new CarAn(1, "Audi", new Timestamp(System.currentTimeMillis()), bodyCar, engine, transmission);
        CarAn bmw = new CarAn(2, "BMW", new Timestamp(System.currentTimeMillis()), bodyCar, engine, transmission);
        store.addEntity(engine);
        store.addEntity(bodyCar);
        store.addEntity(transmission);
        store.addEntity(audi);
        store.addEntity(bmw);
        store.deleteEntity(audi);
        assertThat(
                store.getAllCarAn().size(),
                is(1)
        );
        factory.close();
    }

    @Test
    public void update() {
        SessionFactory factory = TransactionRollBack.create(HibernateSessionFactoryUtil.getFactory());
        HibernateStore store = new HibernateStore(factory);
        EngineAn engineOne = new EngineAn("3.5 turbo");
        EngineAn engineTwo = new EngineAn("2.5 tdi");
        BodyCarAn bodyCar = new BodyCarAn("hatchback");
        TransmissionAn transmission = new TransmissionAn("all-road");
        CarAn car = new CarAn(0, "Audi", new Timestamp(System.currentTimeMillis()), bodyCar, engineOne, transmission);
        store.addEntity(engineOne);
        store.addEntity(engineTwo);
        store.addEntity(bodyCar);
        store.addEntity(transmission);
        store.addEntity(car);
        car.setEngineAn(engineTwo);
        store.updateEntity(car);
        assertThat(
                store.getCarAnById(car).getEngineAn().getName(),
                is("2.5 tdi")
        );
        factory.close();
    }

//    /**
//     * Clean tests data.
//     */
//    @AfterClass
//    public static void close() {
//        HibernateSessionFactoryUtil.getFactory().close();
//    }
}
