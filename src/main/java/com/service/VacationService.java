package com.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonSyntaxException;

public interface VacationService {

    boolean create(HttpServletRequest req) throws JsonSyntaxException, IOException;

    String getAllJson(Long id);
}
