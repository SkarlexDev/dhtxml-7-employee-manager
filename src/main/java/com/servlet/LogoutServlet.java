package com.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.HttpHandler;

public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final Logger log = Logger.getLogger(LogoutServlet.class.getName());

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        log.info("Request to logout");
        HttpHandler.handle(res);
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
            Cookie[] cookies = req.getCookies();
            if (cookies != null) for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/employee-manager");
                cookie.setMaxAge(0);
                res.addCookie(cookie);
            }
        } else {
            res.sendRedirect("login");
        }
    }
}
