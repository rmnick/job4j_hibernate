package ru.job4j.controller;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * it's not using!!!
 */
public class AuthFilter implements Filter {
    public final static Logger LOG = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void init(FilterConfig fConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            if (!req.getRequestURI().contains("/select")) {
                LOG.info("in filter");
                chain.doFilter(req, resp);
            } else {
                HttpSession session = req.getSession(false);
                if (session != null && session.getAttribute("login") != null) {
                    chain.doFilter(req, resp);
                } else {
                    resp.sendRedirect("/index.html");
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (ServletException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
