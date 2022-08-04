package com.service;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import jakarta.servlet.http.HttpServletRequest;

public interface EmployeeService {

	String getAllJson();

	boolean create(HttpServletRequest req) throws JsonSyntaxException, IOException;

	boolean delete(HttpServletRequest req) throws IOException;
	
}
