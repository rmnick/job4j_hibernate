package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Person;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EnterServlet extends HttpServlet {
    public final static Logger LOG = Logger.getLogger(EnterServlet.class.getName());
    private final Service service = Service.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BufferedReader reader = req.getReader();
            PrintWriter pw = resp.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            Person person = mapper.readValue(reader.readLine(), Person.class);
            if (service.validateEnter(person)) {
                HttpSession session = req.getSession(true);
                session.setAttribute("login", person.getLogin());
                Cookie cookieLog = new Cookie("login", person.getLogin());
                Cookie cookiePassword = new Cookie("password", person.getPassword());
                cookiePassword.setMaxAge(-1);
                resp.addCookie(cookieLog);
                resp.addCookie(cookiePassword);
                pw.write("index.html");
                reader.close();
                pw.close();
            } else {
                pw.write("");
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
