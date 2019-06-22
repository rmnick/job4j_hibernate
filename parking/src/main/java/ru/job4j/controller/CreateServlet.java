package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateServlet extends HttpServlet {
    private final static Logger LOG = Logger.getLogger(CreateServlet.class.getName());
    private final Service service = Service.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            BufferedReader br = req.getReader();
            ObjectMapper mapper = new ObjectMapper();
            Person person = mapper.readValue(br.readLine(), Person.class);
            PrintWriter pw = resp.getWriter();
            String result = service.validateNotExist(person);
            if (result.equals(Service.SUCCESS) && service.addEntity(person) != null) {
                HttpSession session = req.getSession();
                session.setAttribute("login", person.getLogin());
                pw.write(result);
            } else {
                pw.write(result);
            }
            pw.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
