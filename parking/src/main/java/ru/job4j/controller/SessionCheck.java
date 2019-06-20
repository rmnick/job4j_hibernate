package ru.job4j.controller;


import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionCheck extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("!!!!! in check");
        resp.setContentType("text/plain");
        HttpSession session = req.getSession(false);
//        ObjectMapper mapper = new ObjectMapper();
        if (session != null && session.getAttribute("login") != null) {
            System.out.println(session.getAttribute("login"));
//            mapper.writeValue(pw, session.getAttribute("login"));
            resp.getWriter().write(session.getAttribute("login").toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
