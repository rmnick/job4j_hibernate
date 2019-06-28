package ru.job4j.controller;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    public final static Logger LOG = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            if (req.getRequestURI().contains("/index.html")) {
                LOG.info("in filter");
                chain.doFilter(req, resp);
            } else {
                HttpSession session = req.getSession(false);
                if (session == null) {
                    resp.sendRedirect("/index.html");
                } else {
                    chain.doFilter(req, resp);
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (ServletException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
