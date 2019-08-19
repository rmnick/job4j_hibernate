package ru.job4j.service;

import org.apache.log4j.Logger;
import ru.job4j.controller.ShowAdvt;
import ru.job4j.service.entities.Advertisement;
import ru.job4j.service.entities.View;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ViewService implements IViewService {
    private static final Logger LOG = Logger.getLogger(ViewService.class.getName());
    public static final IViewService INSTANCE = new ViewService();

    private ViewService() {
    }

    public static IViewService getInstance() {
        return INSTANCE;
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
