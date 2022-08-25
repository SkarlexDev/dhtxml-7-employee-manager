package com.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonSyntaxException;

public interface VacationService {

    int create(HttpServletRequest req) throws JsonSyntaxException, IOException;

    String getAllJson(Long id);
    
    String getAllJsonWithPending(Long id);
    
    boolean acceptVacation(String action, HttpServletRequest req) throws IOException;
    
    void declineVacation(String action, HttpServletRequest req) throws IOException;

	boolean editVacation(HttpServletRequest req) throws JsonSyntaxException, IOException;
}
