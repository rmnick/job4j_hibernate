package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Brand;
import ru.job4j.service.entities.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SelectServlet extends HttpServlet {
    private final Service service = Service.getInstance();
    public final static Logger LOG = Logger.getLogger(SelectServlet.class.getName());
    public final static String BRAND = "brand";
    public final static String MODEL = "model";

    /**
     * send to user all car brands from DB
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Brand> brands = service.getAllBrands();
            String result = objectMapper.writeValueAsString(brands);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/json");
            PrintWriter writer = resp.getWriter();
            writer.print(result);
            writer.flush();

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * get json from user and depending on the condition(brand or model) send the data of the model or parts
     * @param req
     * @param resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PrintWriter writer = resp.getWriter();
            resp.setContentType("text/json");
            resp.setCharacterEncoding("UTF-8");
            Enumeration<String> enumeration = req.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String param = enumeration.nextElement();
                if (param.equals(BRAND)) {
                    String brandStr = req.getParameter(BRAND);
                    Brand brand = new Brand(brandStr);
                    List<String> models = service.getAllModelsNamesByBrand(brand);
                    String result = mapper.writeValueAsString(models);
                    writer.print(result);
                } else if (param.equals(MODEL)) {
                    String modeldStr = req.getParameter(MODEL);
                    Model model = new Model(modeldStr);
                    List<String> engines = service.getAllEnginesByModel(model);
                    List<String> transmissions = service.getAllTransmissionsByModel(model);
                    List<String> bodies = service.getAllBodyCarsByModel(model);
                    List<List<String>> allParts = new ArrayList<>();
                    allParts.add(engines);
                    allParts.add(transmissions);
                    allParts.add(bodies);
                    String result = mapper.writeValueAsString(allParts);
                    writer.print(result);
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
