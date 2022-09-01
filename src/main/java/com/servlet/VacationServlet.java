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
		String filter = req.getParameter("filter");
		res.setContentType("text/json");
		if(filter!=null) {
			try {
				Long id = Long.parseLong(req.getParameter("id"));
				res.getWriter().println(vacationService.getAllJsonWithPending(id));
			} catch (Exception e) {
				res.sendRedirect("login");
			}
		}else {
			try {
				Long id = Long.parseLong(req.getParameter("id"));
				res.getWriter().println(vacationService.getAllJson(id));
			} catch (Exception e) {
				res.sendRedirect("login");
			}
		}		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.info("Request to create employee vacation entry");
		HttpHandler.handle(res);
		String action = req.getParameter("action");
		int status = 0;
		switch (action) {
		case "add" -> {
			log.info("Create new vacation request");
			status = vacationService.create(req);
			break;
		}
		case "Accepted" -> {
			log.info("Accept vacation");
			if (!vacationService.acceptVacation(action, req)) {
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				res.getWriter().write("Employee has insufficient remaining vacation days!");
			}			
			break;
		}
		
		case "edit" -> {
			log.info("Edit vacation");
			if(!vacationService.editVacation(req)) {
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				res.getWriter().write("Failed to update vacation data!");
			}
			break;
		}
		
		case "Declined" -> {
			log.info("Decline vacation");
			vacationService.declineVacation(action, req);
			break;
		}
		default -> {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			break;
		}

		}

		if (status == 400) {
			res.getWriter().write("Failed to create vacation");
			res.setStatus(status);
		}
		if (status == 409) {
			res.getWriter().write("Invalid vacation period!");
			res.setStatus(status);
		}
	}
}
