package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.entities.Person;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EnterServlet extends HttpServlet {
    public final static Logger LOG = Logger.getLogger(EnterServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader;
        PrintWriter pw;
        ObjectMapper mapper = new ObjectMapper();
        Person person;
        try {
            reader = req.getReader();
            person = mapper.readValue(reader.readLine(), Person.class);
//            HttpSession session = req.getSession(true);
//            session.setAttribute("login", person.getLogin());
            Cookie cookieLog = new Cookie("login", person.getLogin());
            resp.addCookie(cookieLog);
            pw = resp.getWriter();
            pw.append("index.html");
            pw.flush();
            reader.close();
            pw.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}