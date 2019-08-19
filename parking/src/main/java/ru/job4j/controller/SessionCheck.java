package ru.job4j.controller;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionCheck extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(SessionCheck.class.getName());

    /**
     * checking session on certain attribute "login", if successful sending a "login"
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("text/plain");
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("login") != null) {
                resp.getWriter().write(session.getAttribute("login").toString());
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
