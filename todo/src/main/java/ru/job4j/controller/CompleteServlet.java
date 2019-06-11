package ru.job4j.controller;

import org.apache.log4j.Logger;
import ru.job4j.service.IService;
import ru.job4j.service.Service;
import ru.job4j.service.entity.Task;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompleteServlet extends HttpServlet {
    private final IService<Task> service = Service.getInstance();
    private final static Logger LOG = Logger.getLogger(CompleteServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Task task = service.getById(new Task(Integer.valueOf(req.getParameter("id")), null, null, false));
            task.setIsDone(true);
            service.update(task);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
