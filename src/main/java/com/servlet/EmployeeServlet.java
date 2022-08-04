package com.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import com.service.EmployeeService;
import com.service.impl.EmployeeServiceImpl;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmployeeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(EmployeeServlet.class.getName());

	private final EmployeeService employeeService = new EmployeeServiceImpl();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.info("Request employee json");
		res.setContentType("text/json");
		res.getWriter().println(employeeService.getAllJson());
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        log.info("Request to create new employee");
		if(employeeService.create(req)) {
			res.setStatus(HttpServletResponse.SC_ACCEPTED);
		} else {
			log.info("Duplicate email!");
            res.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
