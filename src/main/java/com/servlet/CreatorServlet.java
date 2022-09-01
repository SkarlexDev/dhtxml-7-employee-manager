package com.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityLayoutServlet;

import com.bean.Employee;
import com.util.HttpHandler;

public class CreatorServlet extends VelocityLayoutServlet {
	private static final long serialVersionUID = 1L;

	private final Logger log = Logger.getLogger(CreatorServlet.class.getName());

	@Override
	public Template handleRequest(HttpServletRequest req, HttpServletResponse res, Context context) {
		log.info("Request to generate creator page");
		HttpHandler.handle(res);
		HttpSession session = req.getSession(false);
		Template template = getTemplate("templates/creator.html");
		if (session != null) {
        	String role = (String) session.getAttribute("role");
        	if(role == null || !allowAccess(role)) {
        		try {
					res.sendRedirect("account");
				} catch (IOException e) {
					e.printStackTrace();
				}
        		return template;
        	}
        	Employee user = (Employee) session.getAttribute("user");
            if (user == null) {
                try {
                    res.sendRedirect("logout");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                res.sendRedirect("login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return template;
	}
	
	private boolean allowAccess(String role) {
        return role.contains("Creator");
    }
	
}
