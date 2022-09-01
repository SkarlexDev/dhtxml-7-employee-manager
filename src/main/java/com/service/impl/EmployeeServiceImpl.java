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
import com.util.KeyGen;
import com.util.LocalDateDeserializer;
import com.util.LocalDateSerializer;


public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = Logger.getLogger(EmployeeServiceImpl.class.getName());

    private final EmployeeDao employeeDao = new EmployeeDaoImpl();
    
    @Override
    public String getAllJson() {
        log.info("Requesting all Employee json");
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        return gsonBuilder.create().toJson(employeeDao.findAll());
    }

    @Override
    public boolean create(HttpServletRequest req) throws JsonSyntaxException, IOException {
        log.info("Parsing json to Employee bean and save");
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Employee bean = (Employee) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), Employee.class);
        bean.setPassword(KeyGen.generateRandomPassword(20));
        bean.setActivation_key(KeyGen.generateRandomPassword(45));
        boolean state = employeeDao.save(bean);
        if(state) {
        	Thread mail = new Thread(new MailServiceImpl(bean));
        	mail.start();
        }
        return state;
    }

    @Override
    public boolean delete(Long id) {
        log.info("Parsing json to id and delete");
        return employeeDao.delete(id);
    }

    @Override
    public boolean update(long parseLong, HttpServletRequest req) throws JsonSyntaxException, IOException {
        log.info("Parsing json to Employee bean and update");
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Employee bean = (Employee) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), Employee.class);
        return employeeDao.update(bean);
    }

	@Override
	public Optional<Employee> getByID(Long id) {
		log.info("Request to find Emmployee by empID");
		return employeeDao.findByEmpID(id);
	}

	@Override
	public Optional<Employee> getByUserNameAndPassword(HttpServletRequest req) throws JsonSyntaxException, IOException {
		log.info("Parse json to user");
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		Employee bean = (Employee) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), Employee.class);
		return employeeDao.findByLoginAndPassword(bean.getEmail(), bean.getPassword());
	}

	@Override
	public boolean activate(HttpServletRequest req) throws JsonSyntaxException, IOException {
		log.info("Parse json to user");
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		Employee bean = (Employee) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), Employee.class);
		if(bean.getAlias() == null || bean.getPassword() == null) {
			return false;
		}else {
			String key = req.getParameter("key");
			return employeeDao.activate(bean, key);
		}
	}

	@Override
	public boolean findByKeyNotActivated(String key) {
		log.info("Verify activation key");
		return employeeDao.findByKey(key);
	}

	@Override
	public boolean addRole(long id) {
		log.info("Update role for user with id " + id);
		return employeeDao.addRole(id);
	}

	@Override
	public boolean removeRole(long id) {
		log.info("Delete role for user with id " + id);
		return employeeDao.removeRole(id);
	}
}
