package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import ru.job4j.service.entities.*;

import java.util.List;

public class ModelStore extends HibernateStore<Model> implements IModelStore {
    private static final IModelStore INSTANCE = new ModelStore(HibernateSessionFactoryUtil.getFactory());
    private static final Logger LOG = Logger.getLogger(ModelStore.class.getName());

    private ModelStore(SessionFactory sf) {
        super(sf);
    }

    public static IModelStore getInstance() {
        return INSTANCE;
    }

    /**
     * get all models names according to brand
     * @param brand Brand
     * @return List String
     */
    public List<String> getAllModelsNamesByBrand(Brand brand) {
        return tx(session -> {
            List<String> result;
            Query query = session.createQuery("select model.name from Model as model inner join model.brand as br with br.name=:name group by model.name");
            query.setParameter("name", brand.getName());
            result = query.list();
            return result;
        });
    }

    /**
     * get all models from DB
     * @return List models
     */
    public List<Model> getAllModels() {
        return tx(session -> {
            return session.createCriteria(Model.class).list();
        });
    }

    /**
     * get model from DB according to parameters
     * @param model Model
     * @param engine Engine
     * @param transmission Transmission
     * @param bodyCar BodyCar
     * @return Model model
     */
    public Model getModelByParam(Model model, Engine engine, Transmission transmission, BodyCar bodyCar) {
        return tx(session -> {
            Model result;
            Query query = session.createQuery("from Model as model where model.name=:name and model.engine.name=:eName and model.transmission.name=:tName and model.bodyCar.name=:bName");
            query.setParameter("name", model.getName());
            query.setParameter("eName", engine.getName());
            query.setParameter("tName", transmission.getName());
            query.setParameter("bName", bodyCar.getName());
            result = (Model) query.uniqueResult();
            return result;
        });
    }
}
