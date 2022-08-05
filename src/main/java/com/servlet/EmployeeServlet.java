package com.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import com.service.EmployeeService;
import com.service.impl.EmployeeServiceImpl;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(EmployeeServlet.class.getName());

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        log.info("Request employee json");
        res.setContentType("text/json");
        res.getWriter().println(employeeService.getAllJson());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String action = req.getParameter("action");
        System.out.println(action);
        switch (action) {
            case "add": {
                log.info("Request to create new employee");
                if (employeeService.create(req)) {
                    res.setStatus(HttpServletResponse.SC_ACCEPTED);
                } else {
                    log.info("Duplicate email!");
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                }
                break;
            }
            case "edit": {
                log.info("Request to edit employee");
                if (employeeService.update(Long.parseLong(req.getParameter("id")), req)) {
                    res.setStatus(HttpServletResponse.SC_ACCEPTED);
                } else {
                    log.info("Invalid data!");
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                }
                break;
            }
            case "delete": {
                log.info("Request to delete employee");
                if (employeeService.delete(Long.parseLong(req.getParameter("id")))) {
                    res.setStatus(HttpServletResponse.SC_ACCEPTED);
                } else {
                    log.info("Id not found");
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                }
                break;
            }
            default: {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
            }
        }
    }
}
