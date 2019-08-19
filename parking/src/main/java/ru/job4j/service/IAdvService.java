package ru.job4j.service;

import ru.job4j.service.entities.Advertisement;
import ru.job4j.service.entities.Car;

import java.util.List;
import java.util.Map;

public interface IAdvService extends IService<Advertisement> {
    List<Advertisement> getAds(Map<String, String> params);
    Advertisement updateSoldAd(Advertisement advert);
    List<Advertisement> getAdsByLogin(String login);
    Advertisement addAdvt(Car car, Advertisement advt);
    Long getNumberOfRows();

}
