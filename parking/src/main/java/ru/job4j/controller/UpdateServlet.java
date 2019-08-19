package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.IPersonService;
import ru.job4j.service.PersonService;
import ru.job4j.service.Service;
import ru.job4j.service.entities.Person;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(CreateServlet.class.getName());
    private final IPersonService service = PersonService.getInstance();

    /**
     * update person data
     * using login for getting real person data from DB, comparing, validation, updating
     * @param req
     * @param resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            BufferedReader br = req.getReader();
            ObjectMapper mapper = new ObjectMapper();
            Person person = mapper.readValue(br.readLine(), Person.class);
            PrintWriter pw = resp.getWriter();
            HttpSession session = req.getSession(false);
            if (session != null && !session.getAttribute("login").equals("")) {
                Person personOld = service.getPersonByLogin(new Person(session.getAttribute("login").toString()));
                String result = service.validateForUpdate(personOld, person);
                if (result.equals(Service.SUCCESS)) {
                    person.setId(personOld.getId());
                    person.setLogin(personOld.getLogin());
                    service.updateEntity(person);
                    pw.write(result);
                } else {
                    pw.write(result);
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
