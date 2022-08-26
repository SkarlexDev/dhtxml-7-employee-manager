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

public class ActivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final Logger log = Logger.getLogger(ActivateServlet.class.getName());

	private final EmployeeService employeeService = new EmployeeServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.info("Creating activation page");
		HttpHandler.handle(res);
		String key = req.getParameter("key");
		System.out.println(key);
		if(!employeeService.findByKeyNotActivated(key)) {
			res.sendRedirect("login");
		}else {
			req.getRequestDispatcher("pages/activate.html").forward(req, res);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.info("Request to activate account");
		HttpHandler.handle(res);
		if (!employeeService.activate(req)) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.getWriter().print("Failed to update");
		}

	}
}
