package com.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Vacation;
import com.dao.EmployeeDao;
import com.dao.VacationDao;
import com.dao.impl.EmployeeDaoImpl;
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

	private final EmployeeDao employeeDao = new EmployeeDaoImpl();

	@Override
	public int create(HttpServletRequest req) throws JsonSyntaxException, IOException {
		log.info("Parsing json to vacation");
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
		Vacation bean = (Vacation) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), Vacation.class);
		bean.setStatus("Pending");
		List<Vacation> db = vacationDao.findAll();
		if (dbDateCheck(db, bean)) {
			return HttpServletResponse.SC_CONFLICT;
		}
		return vacationDao.save(bean) ? HttpServletResponse.SC_ACCEPTED : HttpServletResponse.SC_BAD_REQUEST;
	}

	@Override
	public String getAllJson(Long id) {
		log.info("Requesting all Employee vacation json");
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		return gsonBuilder.create().toJson(vacationDao.findAllByEmployee(id));
	}

	@Override
	public String getAllJsonWithPending(Long id) {
		log.info("Requesting all Employee vacation json with pending status");
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		return gsonBuilder.create().toJson(vacationDao.findAllByEmployeeWithPending(id));
	}

	private boolean dbDateCheck(List<Vacation> db, Vacation v) {
		return db.stream().anyMatch((dbv) -> Objects.equals(dbv.getEmployeeId(), v.getEmployeeId())
				&& ((dbv.getVacationFrom().isBefore(v.getVacationFrom())
						|| dbv.getVacationFrom().equals(v.getVacationFrom())))
				&& ((v.getVacationFrom().isBefore(dbv.getVacationTo())
						|| v.getVacationFrom().equals(dbv.getVacationTo())))
				&& dbv.getStatus().equals("Accepted"));

	}

	@Override
	public boolean acceptVacation(String action, HttpServletRequest req) throws IOException {
		log.info("Request to accept vacation");
		Long id = JsonToStringUtil.formatLong(req);
		return handleVacation(action, id);

	}

	@Override
	public void declineVacation(String action, HttpServletRequest req) throws IOException {
		log.info("Request to decline vacation");
		Long id = JsonToStringUtil.formatLong(req);
		vacationDao.handleVacationStatus(action, id);

	}

	private boolean handleVacation(String action, Long id) {
		Optional<Vacation> vacation = vacationDao.findById(id);
		if (vacation.isPresent()) {
			Vacation bean = vacation.get();
			Long days = employeeDao.checkRemainingDays(id);
			Long requestedDays = bean.getVacationFrom().until(bean.getVacationTo(), ChronoUnit.DAYS);
			Long result = days - requestedDays;
			if (result > 0) {
				employeeDao.updateEmployeeVacationDays(result, bean.getEmployeeId());
				vacationDao.handleVacationStatus(action, id);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean editVacation(HttpServletRequest req) throws JsonSyntaxException, IOException {
		log.info("Request to change vacation data");
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
		Vacation bean = (Vacation) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), Vacation.class);
		return vacationDao.update(bean);
	}
}
