package com.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityLayoutServlet;

import com.bean.Employee;
import com.bean.User;
import com.service.EmployeeService;
import com.service.impl.EmployeeServiceImpl;
import com.util.HttpHandler;

public class AccountServlet extends VelocityLayoutServlet {

	private static final long serialVersionUID = 1L;

	private final EmployeeService employeeService = new EmployeeServiceImpl();

	@Override
	protected Template handleRequest(HttpServletRequest req, HttpServletResponse res, Context context) {
		HttpHandler.handle(res);
		Template template = getTemplate("templates/account.html");
		String id = req.getParameter("id");
		Employee bean = null;
		HttpSession session = req.getSession(false);
		if (session == null) {
			try {
				res.sendRedirect("login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (id != null) {
				Optional<Employee> emp = employeeService.getByID(Long.parseLong(id));
				if (emp.isPresent()) {
					bean = emp.get();
				} else {
					try {
						res.sendRedirect("login");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				User user = (User) session.getAttribute("user");
				if (user != null) {
					Optional<Employee> emp = employeeService.getByID(user.getEmp_id());
					if (emp.isPresent()) {
						bean = emp.get();
					}
				}
			}
		}

		context.put("user", bean);
		return template;
	}
}
