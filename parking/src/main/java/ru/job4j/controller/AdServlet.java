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
    public static final String ENGINE = "engine";
    public static final String MODEL = "model";
    public static final String TRANSMISSION = "transmission";
    public static final String BODY = "bodyCar";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String YEAR = "month";
    public static final String MILEAGE = "mileage";
    public static final String LOGIN = "login";
    public static final String PATH_DEFAULT_IMG = "default";
    public static final String NAME_DEFAULT_IMG = "car.png";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String filePath = getServletContext().getRealPath("/")
                    + PATH_DEFAULT_IMG
                    + File.separator
                    + NAME_DEFAULT_IMG;
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
