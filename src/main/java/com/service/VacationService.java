package com.service;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import jakarta.servlet.http.HttpServletRequest;

public interface VacationService {

    boolean create(HttpServletRequest req) throws JsonSyntaxException, IOException;

    String getAllJson(Long id);
}
