package com.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.DataService;
import com.service.impl.DataServiceImpl;
import com.util.HttpHandler;

public class DataServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(DataServlet.class.getName());
	
	private DataService data = new DataServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String request = req.getParameter("request");
		HttpHandler.handle(res);
		if(request != null) {
			switch (request) {
            case "employee-info" -> {
                log.info("Request employee role data");
                res.setContentType("text/json");
                res.getWriter().println(data.getEmployeeJson());
                break;
            }
            case "vacations-info" -> {
            	log.info("Request vacation data");
            	res.setContentType("text/json");
        		res.getWriter().println(data.getVacationJson());               
                break;
            }
            case "accounts-info" -> {
            	log.info("Request accounts status data");
            	res.setContentType("text/json");
        		res.getWriter().println(data.getAccountsStatusJson());    
                break;
            }
            default -> {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
            }
        }
		}
	}

}
