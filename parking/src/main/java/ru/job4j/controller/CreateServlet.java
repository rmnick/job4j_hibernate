package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Person;

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

    /**
     * create person by session attribute "login" and send all data to "updating" page
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PrintWriter wr = resp.getWriter();
            HttpSession session = req.getSession(false);
            if (session != null && !session.getAttribute("login").toString().equals("")) {
                Person person = service.getPersonByLogin(new Person(session.getAttribute("login").toString()));
                Person showPerson = new Person();
                showPerson.setName(person.getName());
                showPerson.setEmail(person.getEmail());
                showPerson.setPhone(person.getPhone());
                mapper.writeValue(wr, showPerson);
                wr.flush();
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * get all data from "registration" page, validate its and create person
     * @param req
     * @param resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            BufferedReader br = req.getReader();
            ObjectMapper mapper = new ObjectMapper();
            Person personNew = mapper.readValue(br.readLine(), Person.class);
            PrintWriter pw = resp.getWriter();

            String result = service.validateNotExist(personNew);
            if (result.equals(Service.SUCCESS) && service.addEntity(personNew) != null) {
                HttpSession session = req.getSession();
                session.setAttribute("login", personNew.getLogin());
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
