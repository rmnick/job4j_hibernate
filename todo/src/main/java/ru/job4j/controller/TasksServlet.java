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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TasksServlet extends HttpServlet {
    private final IService<Task> service = Service.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in servlet");
        List<Task> tasks = service.getAll();
//        tasks.forEach(System.out::println);
        System.out.println("size!!!!!!!!");
        System.out.println(tasks.size());
        System.out.println("end of size!!!");
        System.out.println("result!!!!!");
        System.out.println(tasks.get(0));

        List<Task> result = new ArrayList<>();
        result.add(new Task(1, "test", new Timestamp(System.currentTimeMillis()).toLocalDateTime(), false));
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(pw, result);
        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
