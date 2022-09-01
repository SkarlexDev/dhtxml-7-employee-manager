package com.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.EmployeeService;
import com.service.impl.EmployeeServiceImpl;
import com.util.HttpHandler;

public class EmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(EmployeeServlet.class.getName());

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        log.info("Request employee json");
        HttpHandler.handle(res);
        res.setContentType("text/json");
        res.getWriter().println(employeeService.getAllJson());
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String action = req.getParameter("action");
        HttpHandler.handle(res);
        switch (action) {
            case "add" -> {
                log.info("Request to create new employee");
                if (!employeeService.create(req)) {
                	log.info("Duplicate email!");
                	res.setStatus(HttpServletResponse.SC_CONFLICT);
                }
                break;
            }
            case "edit" -> {
                log.info("Request to edit employee");
                if (!employeeService.update(Long.parseLong(req.getParameter("id")), req)) {
                    log.info("Invalid data!");
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                }
                break;
            }
            case "delete" -> {
                log.info("Request to delete employee");
                if (!employeeService.delete(Long.parseLong(req.getParameter("id")))) {
                    log.info("Id not found");
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                }
                break;
            }
            case "add-admin" -> {
                log.info("Request to add admin role to employee");
                if (!employeeService.addRole(Long.parseLong(req.getParameter("id")))) {
                    log.info("Failed to add role");
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                }
                break;
            }
            case "remove-admin" -> {
                log.info("Request to remove admin role to employee");
                if (!employeeService.removeRole(Long.parseLong(req.getParameter("id")))) {
                    log.info("Failed to remove role");
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                }
                break;
            }
            default -> {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
            }
        }
    }
}
