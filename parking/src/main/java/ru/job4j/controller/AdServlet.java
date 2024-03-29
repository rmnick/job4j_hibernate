package ru.job4j.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import ru.job4j.service.*;
import ru.job4j.service.entities.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdServlet extends HttpServlet {
    private final static Logger LOG = Logger.getLogger(AdServlet.class.getName());
    private final Map<String, String> attributes = new HashMap<>();
    private final IModelService modelService = ModelService.getInstance();
    private final IPersonService personService = PersonService.getInstance();
    private final IAdvService advService = AdvertService.getInstance();
    public static final String BRAND = "brand";
    public static final String ENGINE = "engine";
    public static final String MODEL = "model";
    public static final String TRANSMISSION = "transmission";
    public static final String BODY = "bodyCar";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String YEAR = "month";
    public static final String MILEAGE = "mileage";
    public static final String LOGIN = "login";


    /**
     * load all data from user page and create new advert
     * @param req
     * @param resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String filePath = null;
            // creating the directory for img storage if it does not exist
            //for win & linux, get path from web.xml
            String uploadDirUserName = String.format("%s%s%s", req.getServletContext().getInitParameter("file-upload"),
                    File.separator,
                    req.getSession(false).getAttribute("login").toString());
            File uploadDir = new File(uploadDirUserName);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            //upload all items from client
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    attributes.put(item.getFieldName(), item.getString());

                } else if (!item.isFormField()) {
                    if (item.getSize() > 0) {
                        String fileName = new File(item.getName()).getName();
                        String[] arr = fileName.split("\\.");
                        String ext = arr[arr.length - 1];
                        filePath = uploadDirUserName
                                + File.separator
                                + String.format("%s.%s", String.valueOf(System.currentTimeMillis()), ext);
                        File storeFile = new File(filePath);
                        System.out.println(filePath);
                        // saves the file on disk
                        item.write(storeFile);
                    }
                }
            }

            //create fields for Model
            Model model = new Model(attributes.get(MODEL));
            Engine engine = new Engine(attributes.get(ENGINE));
            Transmission transmission = new Transmission(attributes.get(TRANSMISSION));
            BodyCar bodyCar = new BodyCar(attributes.get(BODY));
            model = modelService.getModelByParam(model, engine, transmission, bodyCar);

            //create fields for Car
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            Timestamp year = new Timestamp(dateFormat.parse(attributes.get(YEAR)).getTime());
            Car car = new Car(model, year, Integer.valueOf(attributes.get(MILEAGE)), Integer.valueOf(attributes.get(PRICE)));

            //create advt
            Person person = new Person(req.getSession(false).getAttribute(LOGIN).toString());
            person = personService.getPersonByLogin(person);
            String desc = String.format("%s %s, %s, %s. %s %s",
                    attributes.get(BRAND),
                    attributes.get(MODEL),
                    attributes.get(ENGINE),
                    attributes.get(YEAR),
                    System.lineSeparator(),
                    attributes.get(DESCRIPTION));
            Advertisement advt = new Advertisement(person, new Timestamp(System.currentTimeMillis()), desc, car, filePath, false);

            //add advert to DB
            advService.addEntity(advt);

            resp.sendRedirect("/index.html");
        } catch (FileUploadException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
