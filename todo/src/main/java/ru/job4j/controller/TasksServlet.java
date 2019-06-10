package ru.job4j.controller;

import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.IService;
import ru.job4j.service.Service;
import ru.job4j.service.entity.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class TasksServlet extends HttpServlet {
    private final IService<Task> service = Service.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in servlet");
        List<Task> tasks = service.getAll();
        tasks.forEach(System.out::println);
        resp.setContentType("text/json");
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(pw, service.getAll());
        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
