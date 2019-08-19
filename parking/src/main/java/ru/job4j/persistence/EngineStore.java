package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import ru.job4j.service.entities.Engine;
import ru.job4j.service.entities.Model;

import java.util.List;

public class EngineStore extends HibernateStore<Engine> implements IEngineStore {
    private static final IEngineStore INSTANCE = new EngineStore(HibernateSessionFactoryUtil.getFactory());
    private static final Logger LOG = Logger.getLogger(EngineStore.class.getName());

    public EngineStore(final SessionFactory sf) {
        super(sf);
    }

    public static IEngineStore getInstance() {
        return INSTANCE;
    }

    /**
     * get all engines from DB according to model
     * @param model Model
     * @return List String
     */
    public List<String> getAllEnginesByModel(Model model) {
        return tx(session -> {
            List<String> result;
            Query query = session.createQuery("select engine.name from Model as model inner join model.engine as engine with model.name=:name group by engine.name");
            query.setParameter("name", model.getName());
            result = query.list();
            return result;
        });
    }
}
