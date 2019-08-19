package ru.job4j.controller;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionEnd extends HttpServlet {
    public static final Logger LOG = Logger.getLogger(SessionEnd.class.getName());

    /**
     * destroy session upon users request
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        LOG.info("session destroy");
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
