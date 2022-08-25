package com.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.bean.Vacation;
import com.dao.VacationDao;
import com.dao.impl.VacationDaoImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.service.CSVService;

public class CSVServiceImpl implements CSVService {

	private static final Logger log = Logger.getLogger(CSVServiceImpl.class.getName());

	private final VacationDao vacationDao = new VacationDaoImpl();

	@Override
	public List<String> getAllVacation() throws IOException {
		log.info("Request to create csv");
		CsvMapper mapper = new CsvMapper();
		mapper.findAndRegisterModules();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		CsvSchema schema = createSchema();
		List<String> csv = new ArrayList<>();
		csv.add(mapper.writer(schema).writeValueAsString(vacationDao.findAll()));
		return csv;
	}

	public CsvSchema createSchema() throws IOException {
		return CsvSchema.builder()
				.addColumn("vacationFrom", CsvSchema.ColumnType.NUMBER_OR_STRING)
				.addColumn("vacationTo", CsvSchema.ColumnType.NUMBER_OR_STRING)
				.addColumn("reason", CsvSchema.ColumnType.STRING)
				.addColumn("status", CsvSchema.ColumnType.STRING)
				.addColumn("employeeId", CsvSchema.ColumnType.NUMBER)
				.build().withColumnSeparator(',').withLineSeparator("\n").withHeader();
	}

	@Override
	public String upload(InputStream fileInputStream) throws IOException {
		log.info("Parse csv to object and validate");
		StringBuffer resp = new StringBuffer();
		CsvMapper mapper = new CsvMapper();
		mapper.findAndRegisterModules();
		mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
		CsvSchema schema = createSchema();
		MappingIterator<Vacation> list = mapper.readerWithTypedSchemaFor(Vacation.class).with(schema)
				.readValues(fileInputStream);
		List<Vacation> result = list.readAll();
		if (result.size() == 0) {
			return resp.append("No file detected!").toString();
		}
		HashSet<String> temp = new HashSet<>();
		AtomicInteger row = new AtomicInteger();
		List<Vacation> db = vacationDao.findAll();
		result.forEach(v -> {
			row.getAndIncrement();
			if (!nullAndEmptyCheck(v)) {
				if (v.getVacationFrom().isAfter(v.getVacationTo())) {
					resp.append("Invalid date - row: ").append(row.get() + 1).append("</br>");
				}
				if (!temp.add(v.toString())) {
					resp.append("Duplicate - row: ").append(row.get() + 1 + " " + v).append("</br>");
				}
				if (dbCheckDuplicate(db, v)) {
					resp.append("Duplicate database - row: ").append(row.get() + 1).append("</br>");
				}
				if (dbDateCheck(db, v)) {
					resp.append("Invalid Vacation dates - row: ").append(row.get() + 1).append("</br>");
				}
			} else {
				resp.append("Missing cell data - row: ").append(row.get() + 1).append("</br>");
			}
		});
		if (resp.length() == 0) {
			resp.append(vacationDao.saveAll(result) ? "ok" : "Invalid data!");
		}
		return resp.toString();
	}

	private boolean nullAndEmptyCheck(Vacation v) {
		boolean nullVerify = Stream.of(v.getVacationFrom(), v.getVacationTo(), v.getReason(), v.getEmployeeId()).anyMatch(Objects::isNull);
		if (!nullVerify) {
			return !(v.getVacationFrom().toString().length() > 0 && v.getVacationTo().toString().length() > 0
					&& v.getEmployeeId().toString().length() > 0 && v.getReason().length() > 0);
		}
		return true;
	}

	private boolean dbCheckDuplicate(List<Vacation> db, Vacation v) {
		return db.stream()
				.anyMatch(e -> e.getVacationFrom().equals(v.getVacationFrom())
						&& e.getVacationTo().equals(v.getVacationTo()) && e.getReason().equals(v.getReason())
						&& Objects.equals(e.getEmployeeId(), v.getEmployeeId()));
	}

	private boolean dbDateCheck(List<Vacation> db, Vacation v) {
		return db.stream()
				.anyMatch((dbv) -> Objects.equals(dbv.getEmployeeId(), v.getEmployeeId())
						&& dbv.getVacationFrom().isBefore(v.getVacationFrom())
						&& v.getVacationFrom().isBefore(dbv.getVacationTo()));
	}

}
