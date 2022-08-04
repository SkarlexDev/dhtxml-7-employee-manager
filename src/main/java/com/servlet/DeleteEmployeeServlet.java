package com.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import com.service.EmployeeService;
import com.service.impl.EmployeeServiceImpl;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteEmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(DeleteEmployeeServlet.class.getName());

	private final EmployeeService employeeService = new EmployeeServiceImpl();

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.info("Request to delete employee");
		if (employeeService.delete(req)) {
			res.setStatus(HttpServletResponse.SC_ACCEPTED);
		} else {
			log.info("Id not found");
			res.setStatus(HttpServletResponse.SC_CONFLICT);
		}
	}
}
