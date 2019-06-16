package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.service.entities.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

public class HibernateStore implements IStore {
    private final static HibernateStore INSTANCE = new HibernateStore(HibernateSessionFactoryUtil.getFactory());
    private final static Logger LOG = Logger.getLogger(HibernateStore.class.getName());
    private SessionFactory sf;

    private HibernateStore(final SessionFactory sf) {
        this.sf = sf;
    }

    public static HibernateStore getInstance() {
        return INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        T result = null;
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            result = command.apply(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    public <V> V addEntity(V item) {
        return tx(session -> {
            session.save(item);
            return item;
        });
    }

    public <V> V deleteEntity(V item) {
        return tx(session -> {
            session.delete(item);
            return item;
        });
    }

    public <V> V updateEntity(V item) {
        return tx(session -> {
            session.update(item);
            return item;
        });
    }


    /**
     * update single column "sold"
     * @param advert Advertisement
     * @return advert Advertisement
     */
    public Advertisement updateSoldAd(Advertisement advert) {
        return tx(session -> {
            session.createQuery("update Advertisement ad set ad.sold=:boolMark where ad.id=:aValue")
                    .setInteger("aValue", advert.getId())
                    .setBoolean("boolMark", true).executeUpdate();
            return advert;
        });
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

    /**
     * get all adverts
     * @return List<Adverstisement> ads
     */
    public List<Advertisement> getAllAds() {
        return tx(session -> {
            return session.createCriteria(Advertisement.class).list();
        });
    }

    /**
     * get adverts from DB by person login
     * @param login String
     * @return List<Advertisement>
     */
    public List<Advertisement> getAdsByLogin(String login) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery("from Advertisement as ad where ad.person.login=:aLogin order by ad.createDate desc");
            query.setParameter("aLogin", login);
            result = query.list();
            return result;
        });
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

    /**
     * get model from DB according to parameters
     * @param model Model
     * @param engine Engine
     * @param transmission Transmission
     * @param bodyCar BodyCar
     * @return Model model
     */
    public Model getModelByParam(Model model, Engine  engine, Transmission transmission, BodyCar bodyCar) {
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

    /**
     * insert car and advert in DB(DO NOT USE THIS!!!)
     * @param car Car
     * @param advt Advertisement
     * @return advert Advertisement
     */
    public Advertisement addAdvt(Car car, Advertisement advt) {
        return tx(session -> {
            session.save(car);
            session.save(advt);
            return advt;
        });
    }

    /**
     * get all adverts from DB according to some order
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAds(int start, int max, String order) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad order by ad.createDate %s", order));
            query.setFirstResult(start);
            query.setMaxResults(max);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts from DB for last day
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsLastDay(int start, int max, String order, Timestamp date) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.createDate > :aDate order by ad.createDate %s", order));
            query.setFirstResult(start);
            query.setMaxResults(max);
            query.setParameter("aDate", date);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts with photo from DB
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsWithPhoto(int start, int max, String order) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.picturePath is not null order by ad.createDate %s", order));
            query.setFirstResult(start);
            query.setMaxResults(max);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts with photo from DB for last day
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsWithPhotoLast(int start, int max, String order, Timestamp date) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.createDate > :aDate and ad.picturePath is not null order by ad.createDate %s", order));
            query.setFirstResult(start);
            query.setMaxResults(max);
            query.setParameter("aDate", date);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts from DB according to brand
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsByBrand(int start, int max, String brand, String order) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.car.model.brand.name=:aBrand order by ad.createDate %s", order));
            query.setParameter("aBrand", brand);
            query.setFirstResult(start);
            query.setMaxResults(max);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts from DB according to brand for last day
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsByBrandLast(int start, int max, String brand, String order, Timestamp date) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.createDate > :aDate and ad.car.model.brand.name=:aBrand order by ad.createDate %s", order));
            query.setParameter("aBrand", brand);
            query.setFirstResult(start);
            query.setMaxResults(max);
            query.setParameter("aDate", date);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts with photo from DB according to brand
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsByBrandWithPhoto(int start, int max, String brand, String order) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.car.model.brand.name=:aBrand and ad.picturePath is not null order by ad.createDate %s", order));
            query.setParameter("aBrand", brand);
            query.setFirstResult(start);
            query.setMaxResults(max);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts with photo from DB according to brand for last day
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsByBrandWithPhotoLast(int start, int max, String brand, String order, Timestamp date) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.createDate > :aDate and ad.car.model.brand.name=:aBrand and ad.picturePath is not null order by ad.createDate %s", order));
            query.setParameter("aBrand", brand);
            query.setFirstResult(start);
            query.setMaxResults(max);
            query.setParameter("aDate", date);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts from DB according to model
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsByModel(int start, int max, String model, String order) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.car.model.name=:aModel order by ad.createDate %s", order));
            query.setParameter("aModel", model);
            query.setFirstResult(start);
            query.setMaxResults(max);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts from DB according to model for last day
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsByModelLast(int start, int max, String model, String order, Timestamp date) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.createDate > :aDate and ad.car.model.name=:aModel order by ad.createDate %s", order));
            query.setParameter("aModel", model);
            query.setFirstResult(start);
            query.setMaxResults(max);
            query.setParameter("aDate", date);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts with photo from DB according to model
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsByModelWithPhoto(int start, int max, String model, String order) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.car.model.name=:aModel and ad.picturePath is not null order by ad.createDate %s", order));
            query.setParameter("aModel", model);
            query.setFirstResult(start);
            query.setMaxResults(max);
            result = query.list();
            return result;
        });
    }

    /**
     * get all adverts with photo from DB according to model for last day
     * @return List<Advertisement> ads
     */
    public List<Advertisement> getAdsByModelWithPhotoLast(int start, int max, String model, String order, Timestamp date) {
        return tx(session -> {
            List<Advertisement> result;
            Query query = session.createQuery(String.format("from Advertisement as ad where ad.createDate > :aDate and ad.car.model.name=:aModel and ad.picturePath is not null order by ad.createDate %s", order));
            query.setParameter("aModel", model);
            query.setFirstResult(start);
            query.setMaxResults(max);
            query.setParameter("aDate", date);
            result = query.list();
            return result;
        });
    }

    /**
     * get number of rows from advert table
     * @return number of rows Long
     */
    public Long getNumberOfRows() {
        return tx(session -> {
            Long result;
            Query query = session.createQuery("select count(*) from Advertisement");
            result = (Long) query.uniqueResult();
            return result;
        });
    }

    /**
     * get person from DB by login
     * @return person Person
     */
    public Person getPersonByLogin(Person person) {
        return tx(session -> {
           Person result;
           Query query = session.createQuery("from Person where login=:login");
           query.setParameter("login", person.getLogin());
           result = (Person) query.uniqueResult();
           return result;
        });
    }

    /**
     * get person from DB by phone
     * @return person Person
     */
    public Person getPersonByPhone(Person person) {
        return tx(session -> {
            Person result;
            Query query = session.createQuery("from Person where phone=:phone");
            query.setParameter("phone", person.getPhone());
            result = (Person) query.uniqueResult();
            return result;
        });
    }

    /**
     * get person from DB by email
     * @return person Person
     */
    public Person getPersonByEmail(Person person) {
        return tx(session -> {
            Person result;
            Query query = session.createQuery("from Person where email=:email");
            query.setParameter("email", person.getEmail());
            result = (Person) query.uniqueResult();
            return result;
        });
    }
}
