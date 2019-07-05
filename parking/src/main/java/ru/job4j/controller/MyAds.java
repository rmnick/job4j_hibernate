package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Advertisement;
import ru.job4j.service.entities.Car;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MyAds extends HttpServlet {
    private final Service service = Service.getInstance();
    public static final Logger LOG = Logger.getLogger(MyAds.class.getName());
    public static final String DELETE = "delete";
    public static final String SELL = "sell";

    /**
     * send adverts for certain person
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession(false);
            PrintWriter pw = resp.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            List<Advertisement> ads;
            if (session != null && !session.getAttribute("login").equals("")) {
                ads = service.getAdsByLogin(session.getAttribute("login").toString());
                String realPath = getServletContext().getRealPath("/");
                mapper.writeValue(pw, service.createViews(ads, realPath));
                pw.flush();
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * delete or mark as "sold" operations for advert
     * @param req
     * @param resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Advertisement ad;
            Car car;
            String operation = req.getParameter("operation");
            if (operation != null) {
                switch (operation) {
                    case DELETE:
                        car = new Car();
                        car.setId(Integer.valueOf(req.getParameter("id")));
                        service.deleteEntity(car);
                        /*or you can delete only ad without car
                         ad = new Advertisement();
                         ad.setId(Integer.valueOf(req.getParameter("id")));
                         service.deleteEntity(ad);
                        */
                        break;
                    case SELL:
                        ad = new Advertisement();
                        ad.setId(Integer.valueOf(req.getParameter("id")));
                        service.updateSoldAd(ad);
                        break;
                        default:
                            break;
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }
}
