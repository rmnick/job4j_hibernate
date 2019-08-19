package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.BrandStore;
import ru.job4j.persistence.IBrandStore;
import ru.job4j.service.entities.Brand;

import java.util.List;

public class BrandService implements IBrandService {
    public static final IBrandService INSTANCE = new BrandService();
    private static final Logger LOG = Logger.getLogger(BrandService.class.getName());
    private IBrandStore store;

    private BrandService() {
        this.store = BrandStore.getInstance();
    }

    public static IBrandService getInstance() {
        return INSTANCE;
    }

    @Override
    public Brand addEntity(Brand brand) {
        return store.addEntity(brand);
    }

    @Override
    public Brand updateEntity(Brand brand) {
        return store.updateEntity(brand);
    }

    @Override
    public Brand deleteEntity(Brand brand) {
        return store.deleteEntity(brand);
    }

    /**
     * get all brands from DB
     * @return List brands
     */
    public List<Brand> getAllBrands() {
        return store.getAllBrands();
    }
}
