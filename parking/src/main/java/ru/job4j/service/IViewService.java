package ru.job4j.service;


import ru.job4j.service.entities.Advertisement;
import ru.job4j.service.entities.View;

import java.util.List;

public interface IViewService {
    List<View> createViews(List<Advertisement> ads, String realPath);
}
