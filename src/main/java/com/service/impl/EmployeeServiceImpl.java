package com.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.bean.Employee;
import com.dao.EmployeeDao;
import com.dao.impl.EmployeeDaoImpl;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.LongSerializationPolicy;
import com.service.EmployeeService;
import com.util.JsonToStringUtil;
import com.util.LocalDateDeserializer;
import com.util.LocalDateSerializer;


public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = Logger.getLogger(EmployeeServiceImpl.class.getName());

    private final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public String getAllJson() {
        log.info("Requesting all Employee as json");
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        return gsonBuilder.create().toJson(employeeDao.findAll());
    }

    @Override
    public boolean create(HttpServletRequest req) throws JsonSyntaxException, IOException {
        log.info("Parsing json to Employee bean for save");
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Employee bean = (Employee) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), Employee.class);
        return employeeDao.save(bean);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Parsing json to id");
        return employeeDao.delete(id);
    }

    @Override
    public boolean update(long parseLong, HttpServletRequest req) throws JsonSyntaxException, IOException {
        log.info("Parsing json to Employee bean for update");
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Employee bean = (Employee) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), Employee.class);
        return employeeDao.update(bean);
    }

	@Override
	public Optional<Employee> getByID(Long id) {
		return employeeDao.findByEmpID(id);
	}
}
