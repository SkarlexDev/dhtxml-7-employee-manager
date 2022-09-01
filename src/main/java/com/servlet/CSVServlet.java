package com.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.service.CSVService;
import com.service.impl.CSVServiceImpl;
import com.util.HttpHandler;

@MultipartConfig
public class CSVServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(CSVServlet.class.getName());

	private final CSVService csvService = new CSVServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.info("Request to download csv");
		HttpHandler.handle(res);
		HttpSession session = req.getSession(false);
		if (session != null) {
			String role = (String) session.getAttribute("role");
			if(role == null || !role.contains("Admin")) {
				res.sendRedirect("account");
			}
			res.setHeader("Content-disposition", "attachment; filename=sample.csv");
			ServletOutputStream out = res.getOutputStream();
			for (String elem : csvService.getAllVacation()) {
				out.write(elem.getBytes());
			}
		} else {
			log.info("Invalid user");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.info("Request to read uploaded csv file");
		Part filePart = req.getPart("file");
		InputStream fileInputStream = filePart.getInputStream();
		String state = csvService.upload(fileInputStream);
		if (state.contains("Duplicate") || state.contains("Invalid") || state.contains("Missing") || state.contains("No")) {
			res.setStatus(HttpServletResponse.SC_CONFLICT);
			res.getWriter().write(state);
		}
	}
}
