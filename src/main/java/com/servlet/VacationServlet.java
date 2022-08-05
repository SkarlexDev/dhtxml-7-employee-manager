package com.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import com.service.VacationService;
import com.service.impl.VacationServiceImpl;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VacationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(VacationServlet.class.getName());

    private final VacationService vacationService = new VacationServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        log.info("Request employee json");
        res.setContentType("text/json");
        Long id = Long.parseLong(req.getParameter("id"));
        res.getWriter().println(vacationService.getAllJson(id));

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        log.info("Request to create employee vacation entry");
        if (vacationService.create(req)) {
            res.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
