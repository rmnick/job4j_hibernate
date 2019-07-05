package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.controller.ShowAdvt;
import ru.job4j.persistence.HibernateStore;
import ru.job4j.service.entities.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;

public class Service implements IService {
    public static final Logger LOG = Logger.getLogger(Service.class.getName());
    private final HibernateStore store = HibernateStore.getInstance();
    private final static Service INSTANCE = new Service();

    public final static String LOGIN = "login";
    public final static String PHONE = "phone";
    public final static String EMAIL = "email";
    public final static String SUCCESS = "success";
    private final Map<String, Function<Person, Person>> validationMap = new HashMap<>();

    public final static String ALL = "maxadditionalstart";
    public final static String ALL_LAST_DAY = "lastmaxadditionalstart";
    public final static String ALL_PHOTO = "maxadditionalstartphoto";
    public final static String ALL_PHOTO_LAST_DAY = "lastmaxadditionalstartphoto";
    public final static String BRAND_ALL = "maxadditionalstartbrand";
    public final static String BRAND_ALL_LAST_DAY = "lastmaxadditionalstartbrand";
    public final static String BRAND_PHOTO = "maxadditionalstartphotobrand";
    public final static String BRAND_PHOTO_LAST_DAY = "lastmaxadditionalstartphotobrand";
    public final static String MODEL_ALL = "maxadditionalstartmodelbrand";
    public final static String MODEL_ALL_LAST_DAY = "lastmaxadditionalstartmodelbrand";
    public final static String MODEL_PHOTO = "maxadditionalstartphotomodelbrand";
    public final static String MODEL_PHOTO_LAST_DAY = "lastmaxadditionalstartmodelbrand";
    private final Map<String, Function<Map<String, String>, List<Advertisement>>> searchMap = new HashMap<>();

    public static Service getInstance() {
        return INSTANCE;
    }

    /**
     * init the map to check the input fields to exist
     */
    private void initValidationMap() {
        validationMap.put(LOGIN, checkLogin());
        validationMap.put(PHONE, checkPhone());
        validationMap.put(EMAIL, checkEmail());
    }

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

    private Service() {
        initValidationMap();
        initSearchMap();
    }

    /**
     * insert some entity in DB
     */
    public <V> V addEntity(V entity) {
        return store.addEntity(entity);
    }

    /**
     * update some item
     * @param entity
     * @param <V>
     * @return
     */
    public <V> V updateEntity(V entity) {
        return store.updateEntity(entity);
    }

    /**
     * delete item from DB
     * @param entity
     * @param <V>
     * @return
     */
    public <V> V deleteEntity(V entity) {
        return store.deleteEntity(entity);
    }

    /**
     * user data validation for existing
     * @param person Person
     * @return String answer
     */
    public String validateNotExist(Person person) {
        String result = SUCCESS;
        for (String str : validationMap.keySet()) {
            if (validationMap.get(str).apply(person) != null) {
                result = str;
                break;
            }
        }
        return result;
    }

    /**
     * validating data from updating page
     * @param oldPerson Person
     * @param newPerson Person
     * @return answer String
     */
    public String validateForUpdate(Person oldPerson, Person newPerson) {
        Person checkPerson = new Person();
        if (!oldPerson.getEmail().equals(newPerson.getEmail())) {
            checkPerson.setEmail(newPerson.getEmail());
        }
        if (!oldPerson.getPhone().equals(newPerson.getPhone())) {
            checkPerson.setPhone(newPerson.getPhone());
        }
        return validateNotExist(checkPerson);
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
     * get all brands from DB
     * @return List brands
     */
    public List<Brand> getAllBrands() {
        return store.getAllBrands();
    }

    /**
     * get all models names according to brand
     * @param brand Brand
     * @return List String
     */
    public List<String> getAllModelsNamesByBrand(Brand brand) {
        return store.getAllModelsNamesByBrand(brand);
    }

    /**
     * get all models from DB
     * @return List models
     */
    public List<Model> getAllModels() {
        return store.getAllModels();
    }

    /**
     * get all adverts by login from DB
     * @return List adverts
     */
    public List<Advertisement> getAdsByLogin(String login) {
        return store.getAdsByLogin(login);
    }

    /**
     * get all engines from DB according to model
     * @param model Model
     * @return List String
     */
    public List<String> getAllEnginesByModel(Model model) {
        return store.getAllEnginesByModel(model);
    }

    /**
     * get all transmissions from DB according to model
     * @param model Model
     * @return List String
     */
    public List<String> getAllTransmissionsByModel(Model model) {
        return store.getAllTransmissionsByModel(model);
    }

    /**
     * get all car bodies from DB according to model
     * @param model Model
     * @return List String
     */
    public List<String> getAllBodyCarsByModel(Model model) {
        return store.getAllBodyCarsByModel(model);
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
        return store.getModelByParam(model, engine, transmission, bodyCar);
    }

    /**
     * data validation for user sign-in
     * @param person Person
     * @return boolean
     */
    public boolean validateEnter(Person person) {
        boolean result = false;
        Person personFromDb = store.getPersonByLogin(person);
        if  (personFromDb != null && person.getPassword().equals(personFromDb.getPassword())) {
            result = true;
        }
        return result;
    }

    /**
     * get person from DB by person login
     * @param person Person
     * @return person Person
     */
    public Person getPersonByLogin(Person person) {
        return store.getPersonByLogin(person);
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

    /**
     * get person from DB by login
     * @return person Person
     */
    private Function<Person, Person> checkLogin() {
        return person -> {
            return store.getPersonByLogin(person);
        };
    }

    /**
     * get person from DB by phone
     * @return person Person
     */
    private Function<Person, Person> checkPhone() {
        return person -> {
            return store.getPersonByPhone(person);
        };
    }

    /**
     * get person from DB by email
     * @return person Person
     */
    private Function<Person, Person> checkEmail() {
        return person -> {
            return store.getPersonByEmail(person);
        };
    }

    /**
     * getting a list of advertisements from DB and transform it into the convenient form
     * @param ads
     * @param realPath
     * @return List<View> views
     */
    public List<View> createViews(List<Advertisement> ads, String realPath) {
        View view;
        List<View> views = new ArrayList<>();
        byte[] imageData;
        String base64Image;
        try {
            for (int i = 0; i < ads.size(); i++) {
                String picPath = ads.get(i).getPicturePath();
                //if there's not img then use default car.png
                if (picPath == null) {
                    picPath = realPath
                            + ShowAdvt.PATH_DEFAULT_IMG
                            + File.separator
                            + ShowAdvt.NAME_DEFAULT_IMG;
                }
                //convert img to Base64
                File image = new File(picPath);
                imageData = Files.readAllBytes(image.toPath());
                base64Image = Base64.getEncoder().encodeToString(imageData);
                //create object "view" for client
                view = new View();
                view.setImg(base64Image);
                view.setDesc(ads.get(i).getDescription());
                view.setSold(ads.get(i).isSold());
                view.setId(ads.get(i).getId());
                views.add(view);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return views;
    }
}
