package com.service;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bean.Employee;
import com.google.gson.JsonSyntaxException;


public interface EmployeeService {

    String getAllJson();

    boolean create(HttpServletRequest req) throws JsonSyntaxException, IOException;

    boolean delete(Long id);

    boolean update(long parseLong, HttpServletRequest req) throws JsonSyntaxException, IOException;

    Optional<Employee> getByID(Long id);
    
    Optional<Employee> getByUserNameAndPassword(HttpServletRequest req) throws JsonSyntaxException, IOException;

	boolean activate(HttpServletRequest req) throws JsonSyntaxException, IOException;

	boolean findByKeyNotActivated(String key);

}
