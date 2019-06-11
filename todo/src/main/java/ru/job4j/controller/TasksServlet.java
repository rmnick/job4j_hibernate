package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.service.IService;
import ru.job4j.service.Service;
import ru.job4j.service.entity.Task;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

public class TasksServlet extends HttpServlet {
    private final static Logger LOG = Logger.getLogger(TasksServlet.class.getName());
    private final IService<Task> service = Service.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Task> tasks = service.getAll();
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pw = resp.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(pw, tasks);
            pw.flush();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Task task = new Task(0, req.getParameter("desc"), new Timestamp(System.currentTimeMillis()), false);
            service.add(task);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
