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

public class AdminServlet extends VelocityLayoutServlet {
    private static final long serialVersionUID = 1L;
    
    private final Logger log = Logger.getLogger(AdminServlet.class.getName());

    @Override
    public Template handleRequest(HttpServletRequest req, HttpServletResponse res, Context context) {
    	log.info("Request to generate admin page");
        HttpHandler.handle(res);
        HttpSession session = req.getSession(false);
        Template template = getTemplate("templates/admin.html");
        String name = null;
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
            } else {
                name = user.getName();
            }
            try {
                session.setAttribute("visit", true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                res.sendRedirect("login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        context.put("user", name);
        return template;
    }
    
    private boolean allowAccess(String role) {
        return role.contains("Creator") || role.contains("Admin");
    }
}
