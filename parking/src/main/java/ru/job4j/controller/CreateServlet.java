package ru.job4j.controller;

import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.entities.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class CreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader br = req.getReader();
        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(br.readLine(), Person.class);
        System.out.println(person);
        resp.getWriter().write(person.getLogin());
    }
}
