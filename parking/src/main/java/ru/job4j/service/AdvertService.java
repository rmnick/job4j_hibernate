package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.persistence.AdvertStore;
import ru.job4j.persistence.IAdvertStore;
import ru.job4j.service.entities.Advertisement;
import ru.job4j.service.entities.Car;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;

public class AdvertService implements IAdvService {
    public static final IAdvService INSTANCE = new AdvertService();
    private static final Logger LOG = Logger.getLogger(AdvertService.class.getName());
    private IAdvertStore store;
    public static final String ALL = "maxadditionalstart";
    public static final String ALL_LAST_DAY = "lastmaxadditionalstart";
    public static final String ALL_PHOTO = "maxadditionalstartphoto";
    public static final String ALL_PHOTO_LAST_DAY = "lastmaxadditionalstartphoto";
    public static final String BRAND_ALL = "maxadditionalstartbrand";
    public static final String BRAND_ALL_LAST_DAY = "lastmaxadditionalstartbrand";
    public static final String BRAND_PHOTO = "maxadditionalstartphotobrand";
    public static final String BRAND_PHOTO_LAST_DAY = "lastmaxadditionalstartphotobrand";
    public static final String MODEL_ALL = "maxadditionalstartmodelbrand";
    public static final String MODEL_ALL_LAST_DAY = "lastmaxadditionalstartmodelbrand";
    public static final String MODEL_PHOTO = "maxadditionalstartphotomodelbrand";
    public static final String MODEL_PHOTO_LAST_DAY = "lastmaxadditionalstartmodelbrand";
    public final Map<String, Function<Map<String, String>, List<Advertisement>>> searchMap = new HashMap<>();

    /**
     * init map for searching adverts by some options
     */
    private void initSearchMap() {
        searchMap.put(ALL, getAds());
        searchMap.put(ALL_LAST_DAY, getAdsLast());
        searchMap.put(ALL_PHOTO, getAdsWithPhoto());
        searchMap.put(ALL_PHOTO_LAST_DAY, getAdsWithPhotoLast());
        searchMap.put(BRAND_ALL, getAdsByBrand());
        searchMap.put(BRAND_ALL_LAST_DAY, getAdsByBrandLast());
        searchMap.put(BRAND_PHOTO, getAdsByBrandWithPhoto());
        searchMap.put(BRAND_PHOTO_LAST_DAY, getAdsByBrandWithPhotoLast());
        searchMap.put(MODEL_ALL, getAdsByModel());
        searchMap.put(MODEL_ALL_LAST_DAY, getAdsByModelLast());
        searchMap.put(MODEL_PHOTO, getAdsByModelWithPhoto());
        searchMap.put(MODEL_PHOTO_LAST_DAY, getAdsByModelWithPhotoLast());
    }

    private AdvertService() {
        this.store = AdvertStore.getInstance();
        initSearchMap();
    }

    public static IAdvService getInstance() {
        return INSTANCE;
    }

    @Override
    public Advertisement addEntity(Advertisement advertisement) {
        return store.addEntity(advertisement);
    }

    @Override
    public Advertisement updateEntity(Advertisement advertisement) {
        return store.updateEntity(advertisement);
    }

    @Override
    public Advertisement deleteEntity(Advertisement advertisement) {
        return store.deleteEntity(advertisement);
    }

    /**
     * getting ads from DB according options
     * @param params Map
     * @return List ads
     */
    public List<Advertisement> getAds(Map<String, String> params) {
        StringBuilder keyStr = new StringBuilder();
        params.keySet().forEach(key -> {
            keyStr.append(key);
        });
//        System.out.println("from method " + keyStr.toString());
        return searchMap.get(keyStr.toString()).apply(params);
    }

    /**
     * update single column "sold" in ads
     * @param advert Advertisement
     * @return advert Advertisement
     */
    public Advertisement updateSoldAd(Advertisement advert) {
        return store.updateSoldAd(advert);
    }

    /**
     * get all adverts by login from DB
     * @return List adverts
     */
    public List<Advertisement> getAdsByLogin(String login) {
        return store.getAdsByLogin(login);
    }

    /**
     * insert car and advert in DB
     * @param car Car
     * @param advt Advertisement
     * @return advert Advertisement
     */
    public Advertisement addAdvt(Car car, Advertisement advt) {
        return store.addAdvt(car, advt);
    }

    /**
     * get number of rows from advert table
     * @return number of rows Long
     */
    public Long getNumberOfRows() {
        return store.getNumberOfRows();
    }

    /**
     * get all adverts from DB according to some order
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAds() {
        return map -> {
            return store.getAds(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("additional"));
        };
    }

    /**
     * get all adverts from DB for last day
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsLast() {
        return map -> {
            Timestamp date = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MIDNIGHT));
            return store.getAdsLastDay(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("additional"), date);
        };
    }

    /**
     * get all adverts with photo from DB
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsWithPhoto() {
        return map -> {
            return store.getAdsWithPhoto(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("additional"));
        };
    }

    /**
     * get all adverts with photo from DB for last day
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsWithPhotoLast() {
        return map -> {
            Timestamp date = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MIDNIGHT));
            return store.getAdsWithPhotoLast(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("additional"), date);
        };
    }

    /**
     * get all adverts from DB according to brand
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsByBrand() {
        return map -> {
            return store.getAdsByBrand(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("brand"), map.get("additional"));
        };
    }

    /**
     * get all adverts from DB according to brand for last day
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsByBrandLast() {
        return map -> {
            Timestamp date = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MIDNIGHT));
            return store.getAdsByBrandLast(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("brand"), map.get("additional"), date);
        };
    }

    /**
     * get all adverts with photo from DB according to brand
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsByBrandWithPhoto() {
        return map -> {
            return store.getAdsByBrandWithPhoto(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("brand"), map.get("additional"));
        };
    }

    /**
     * get all adverts with photo from DB according to brand for last day
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsByBrandWithPhotoLast() {
        return map -> {
            Timestamp date = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MIDNIGHT));
            return store.getAdsByBrandWithPhotoLast(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("brand"), map.get("additional"), date);
        };
    }

    /**
     * get all adverts from DB according to model
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsByModel() {
        return map -> {
            return store.getAdsByModel(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("model"), map.get("additional"));
        };
    }

    /**
     * get all adverts from DB according to model for last day
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsByModelLast() {
        return map -> {
            Timestamp date = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MIDNIGHT));
            return store.getAdsByModelLast(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("model"), map.get("additional"), date);
        };
    }

    /**
     * get all adverts with photo from DB according to model
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsByModelWithPhoto() {
        return map -> {
            return store.getAdsByModelWithPhoto(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("model"), map.get("additional"));
        };
    }

    /**
     * get all adverts with photo from DB according to model for last day
     * @return List<Advertisement> ads
     */
    private Function<Map<String, String>, List<Advertisement>> getAdsByModelWithPhotoLast() {
        return map -> {
            Timestamp date = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MIDNIGHT));
            return store.getAdsByModelWithPhotoLast(Integer.valueOf(map.get("start")), Integer.valueOf(map.get("max")), map.get("model"), map.get("additional"), date);
        };
    }
}
