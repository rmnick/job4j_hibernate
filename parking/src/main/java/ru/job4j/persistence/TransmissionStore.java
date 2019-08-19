package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import ru.job4j.service.entities.Model;
import ru.job4j.service.entities.Transmission;

import java.util.List;

public class TransmissionStore extends HibernateStore<Transmission> implements ITransmissionStore {
    private static final ITransmissionStore INSTANCE = new TransmissionStore(HibernateSessionFactoryUtil.getFactory());
    private static final Logger LOG = Logger.getLogger(TransmissionStore.class.getName());

    private TransmissionStore(final SessionFactory sf) {
        super(sf);
    }

    public static ITransmissionStore getInstance() {
        return INSTANCE;
    }

    /**
     * get all transmissions from DB according to model
     * @param model Model
     * @return List String
     */
    public List<String> getAllTransmissionsByModel(Model model) {
        return tx(session -> {
            List<String> result;
            Query query = session.createQuery("select transmission.name from Model as model inner join model.transmission as transmission with model.name=:name group by transmission.name");
            query.setParameter("name", model.getName());
            result = query.list();
            return result;
        });
    }
}
