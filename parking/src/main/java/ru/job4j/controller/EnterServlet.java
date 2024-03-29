package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.IPersonService;
import ru.job4j.service.PersonService;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Person;

import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EnterServlet extends HttpServlet {
    public static final Logger LOG = Logger.getLogger(EnterServlet.class.getName());
    private final IPersonService personService = PersonService.getInstance();

    /**
     * get data from "index"(enter) page, checking person for existence in DB,
     * create cookies for login and password, create new session with new attribute for further authentication
     * @param req
     * @param resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            BufferedReader reader = req.getReader();
            PrintWriter pw = resp.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            Person person = mapper.readValue(reader.readLine(), Person.class);
            if (personService.validateEnter(person)) {
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
