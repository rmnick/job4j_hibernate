package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Brand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

public class SelectServlet extends HttpServlet {
    private final Service service = Service.getInstance();
    public final static Logger LOG = Logger.getLogger(SelectServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PrintWriter writer = resp.getWriter();
            resp.setContentType("text/json");
            resp.setCharacterEncoding("UTF-8");
            Enumeration<String> enumeration = req.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String param = enumeration.nextElement();
                if (param.equals("brand")) {
                    String brandStr = req.getParameter("brand");
                    Brand brand = new Brand(brandStr);
                    List<String> models = service.getAllModelsNamesByBrand(brand);
                    String result = mapper.writeValueAsString(models);
                    writer.print(result);
//                } else if(param.equals("model")) {
//                    String modeldStr = req.getParameter("model");
//                    Model model = new Model(modeldStr);
//                    List<String> engines = service.getAllModelsNamesByBrand(brand);
//                    String result = mapper.writeValueAsString(models);
//                    writer.print(result);
                }
            }

            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
