package com.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.VacationService;
import com.service.impl.VacationServiceImpl;
import com.util.HttpHandler;


public class VacationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(VacationServlet.class.getName());

    private final VacationService vacationService = new VacationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        log.info("Request employee json");
        HttpHandler.handle(res);
        res.setContentType("text/json");
        Long id = Long.parseLong(req.getParameter("id"));
        res.getWriter().println(vacationService.getAllJson(id));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        log.info("Request to create employee vacation entry");
        HttpHandler.handle(res);
        if (!vacationService.create(req)) {
        	res.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
