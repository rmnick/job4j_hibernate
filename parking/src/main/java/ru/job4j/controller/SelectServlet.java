package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.*;
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
    private final IBrandService brandService = BrandService.getInstance();
    private final IModelService modelService = ModelService.getInstance();
    private final IEngineService engineService = EngineService.getInstance();
    private final IBodyCarService bodyCarService = BodyCarService.getInstance();
    private final ITransmissionService transmissionService = TransmissionService.getInstance();
    public static final Logger LOG = Logger.getLogger(SelectServlet.class.getName());
    public static final String BRAND = "brand";
    public static final String MODEL = "model";

    /**
     * send to user all car brands from DB
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Brand> brands = brandService.getAllBrands();
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
                    List<String> models = modelService.getAllModelsNamesByBrand(brand);
                    String result = mapper.writeValueAsString(models);
                    writer.print(result);
                } else if (param.equals(MODEL)) {
                    String modeldStr = req.getParameter(MODEL);
                    Model model = new Model(modeldStr);
                    List<String> engines = engineService.getAllEnginesByModel(model);
                    List<String> transmissions = transmissionService.getAllTransmissionsByModel(model);
                    List<String> bodies = bodyCarService.getAllBodyCarsByModel(model);
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
