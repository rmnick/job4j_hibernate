package ru.job4j.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import ru.job4j.service.entities.Advertisement;
import ru.job4j.service.entities.Car;

import java.sql.Timestamp;
import java.util.List;

public class AdvertStore extends HibernateStore<Advertisement> implements IAdvertStore {
    private static final IAdvertStore INSTANCE = new AdvertStore(HibernateSessionFactoryUtil.getFactory());
    private static final Logger LOG = Logger.getLogger(AdvertStore.class.getName());

    private AdvertStore(final SessionFactory sf) {
        super(sf);
    }

    public static IAdvertStore getInstance() {
        return INSTANCE;
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
}
