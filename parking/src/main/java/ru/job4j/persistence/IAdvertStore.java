package ru.job4j.persistence;

import ru.job4j.service.entities.Advertisement;
import ru.job4j.service.entities.Car;

import java.sql.Timestamp;
import java.util.List;

public interface IAdvertStore extends IStore<Advertisement> {
    Advertisement updateSoldAd(Advertisement advert);
    List<Advertisement> getAllAds();
    List<Advertisement> getAdsByLogin(String login);
    Advertisement addAdvt(Car car, Advertisement advt);
    List<Advertisement> getAds(int start, int max, String order);
    List<Advertisement> getAdsLastDay(int start, int max, String order, Timestamp date);
    List<Advertisement> getAdsWithPhoto(int start, int max, String order);
    List<Advertisement> getAdsWithPhotoLast(int start, int max, String order, Timestamp date);
    List<Advertisement> getAdsByBrand(int start, int max, String brand, String order);
    List<Advertisement> getAdsByBrandLast(int start, int max, String brand, String order, Timestamp date);
    List<Advertisement> getAdsByBrandWithPhoto(int start, int max, String brand, String order);
    List<Advertisement> getAdsByBrandWithPhotoLast(int start, int max, String brand, String order, Timestamp date);
    List<Advertisement> getAdsByModel(int start, int max, String model, String order);
    List<Advertisement> getAdsByModelLast(int start, int max, String model, String order, Timestamp date);
    List<Advertisement> getAdsByModelWithPhoto(int start, int max, String model, String order);
    List<Advertisement> getAdsByModelWithPhotoLast(int start, int max, String model, String order, Timestamp date);
    Long getNumberOfRows();
}
