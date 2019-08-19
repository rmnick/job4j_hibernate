package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import ru.job4j.service.entities.BodyCar;
import ru.job4j.service.entities.Model;

import java.util.List;

public class BodyCarStore extends HibernateStore<BodyCar> implements IBodyCarStore {
    private static final IBodyCarStore INSTANCE = new BodyCarStore(HibernateSessionFactoryUtil.getFactory());
    private static final Logger LOG = Logger.getLogger(BodyCarStore.class.getName());

    public BodyCarStore(final SessionFactory sf) {
        super(sf);
    }

    public static IBodyCarStore getInstance() {
        return INSTANCE;
    }


    /**
     * get all car bodies from DB according to model
     * @param model Model
     * @return List String
     */
    public List<String> getAllBodyCarsByModel(Model model) {
        return tx(session -> {
            List<String> result;
            Query query = session.createQuery("select body.name from Model as model inner join model.bodyCar as body with model.name=:name group by body.name");
            query.setParameter("name", model.getName());
            result = query.list();
            return result;
        });
    }
}
