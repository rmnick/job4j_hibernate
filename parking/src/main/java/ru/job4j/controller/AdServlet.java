package ru.job4j.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import ru.job4j.service.Service;
import ru.job4j.service.entities.*;

import javax.servlet.ServletException;
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
    private final Service service = Service.getInstance();
    public final static String ENGINE = "engine";
    public final static String MODEL = "model";
    public final static String TRANSMISSION = "transmission";
    public final static String BODY = "bodyCar";
    public final static String DESCRIPTION = "description";
    public final static String PRICE = "price";
    public final static String YEAR = "month";
    public final static String MILEAGE = "mileage";
    public final static String LOGIN = "login";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String filePath = this.getClass().getClassLoader().getResource("car.png").toString();
            // creates the directory if it does not exist
            //for win & linux
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
                    System.out.println(item.getName());
                    System.out.println(item.getSize());
                    if (item.getSize() > 0) {
                        String fileName = new File(item.getName()).getName();
                        filePath = uploadDirUserName + File.separator + fileName;
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
            model = service.getModelByParam(model, engine, transmission, bodyCar);

            //create fields for Car
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            Timestamp year = new Timestamp(dateFormat.parse(attributes.get(YEAR)).getTime());
            Car car = new Car(model, year, Integer.valueOf(attributes.get(MILEAGE)), Integer.valueOf(attributes.get(PRICE)));

            //create advt
            Person person = new Person(req.getSession(false).getAttribute(LOGIN).toString());
            person = service.getPersonByLogin(person);
            Advertisement advt = new Advertisement(person, new Timestamp(System.currentTimeMillis()), attributes.get(DESCRIPTION), car, filePath, false);

            //add avt and car in one transaction to DB
            service.addAdvt(car, advt);

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
