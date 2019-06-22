package ru.job4j.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionCheck extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("login") != null) {
            System.out.println(session.getAttribute("login"));
            resp.getWriter().write(session.getAttribute("login").toString());
        }
    }
}
