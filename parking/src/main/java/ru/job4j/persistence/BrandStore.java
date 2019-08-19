package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import ru.job4j.service.entities.Brand;

import java.util.List;

public class BrandStore extends HibernateStore<Brand> implements IBrandStore {
    private static final IBrandStore INSTANCE = new BrandStore(HibernateSessionFactoryUtil.getFactory());
    private static final Logger LOG = Logger.getLogger(BrandStore.class.getName());

    private BrandStore(final SessionFactory sf) {
        super(sf);
    }

    public static IBrandStore getInstance() {
        return INSTANCE;
    }

    /**
     * get all brands from DB
     * @return List brands
     */
    public List<Brand> getAllBrands() {
        return tx(session -> {
            return session.createCriteria(Brand.class).list();
        });
    }
}
