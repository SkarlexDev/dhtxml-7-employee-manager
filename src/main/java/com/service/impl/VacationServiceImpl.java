package com.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.bean.Vacation;
import com.dao.VacationDao;
import com.dao.impl.VacationDaoImpl;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.LongSerializationPolicy;
import com.service.VacationService;
import com.util.JsonToStringUtil;
import com.util.LocalDateDeserializer;
import com.util.LocalDateSerializer;

public class VacationServiceImpl implements VacationService {

    private static final Logger log = Logger.getLogger(VacationServiceImpl.class.getName());

    private final VacationDao vacationDao = new VacationDaoImpl();

    @Override
    public boolean create(HttpServletRequest req) throws JsonSyntaxException, IOException {
        log.info("Parsing json to vacation");
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Vacation bean = (Vacation) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), Vacation.class);
        return vacationDao.save(bean);
    }

    @Override
    public String getAllJson(Long id) {
        log.info("Requesting all Employee Vacation as json");
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        return gsonBuilder.create().toJson(vacationDao.findAllByEmployee(id));
    }
}
