package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import ru.job4j.service.*;
import ru.job4j.service.entities.Advertisement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

public class ShowAdvt extends HttpServlet {
    private final IAdvService advService = AdvertService.getInstance();
    private final IViewService viewService = ViewService.getInstance();
    public static final Logger LOG = Logger.getLogger(ShowAdvt.class.getName());
    public static final String PATH_DEFAULT_IMG = "default";
    public static final String NAME_DEFAULT_IMG = "car.png";

    /**
     * send to user number of ads rows for further processing of requests
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().print(advService.getNumberOfRows());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * show all adverts according some options
     * @param req
     * @param resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PrintWriter pw = resp.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            List<Advertisement> ads;
            Map<String, String> map = new HashMap<>();

            //init map with keys aka names and values for service
            Enumeration<String> params = req.getParameterNames();
            while (params.hasMoreElements()) {
                String param = params.nextElement();
                map.put(param, req.getParameter(param));
            }

            ads = advService.getAds(map);
            String realPath = getServletContext().getRealPath("/");
            mapper.writeValue(pw, viewService.createViews(ads, realPath));
            pw.flush();
            pw.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
