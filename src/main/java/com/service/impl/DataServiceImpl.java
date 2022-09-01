package com.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bean.jsonhelper.BeanJsonHelper;
import com.bean.jsonhelper.json.JsonBeanHelper;
import com.dao.DataDao;
import com.dao.impl.DataDaoImpl;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.service.DataService;

public class DataServiceImpl implements DataService {

	private final DataDao dataDao = new DataDaoImpl();

	@Override
	public String getEmployeeJson() {
		return getBuilder().create().toJson(generateEmployeeSortedData(dataDao.findAllEmployee()));
	}

	@Override
	public String getVacationJson() {		
		return getBuilder().create().toJson(generateVacationSortedData(dataDao.findAllVacation()));
	}
	
	@Override
	public String getAccountsStatusJson() {
		return getBuilder().create().toJson(generateAccountStatus(dataDao.findAllAccountsStatus()));
	}
	
	private GsonBuilder getBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		return gsonBuilder;
	}
	
	private List<JsonBeanHelper> generateEmployeeSortedData(BeanJsonHelper dao) {
		return new ArrayList<>(Arrays.asList(				
				new JsonBeanHelper("Creators", dao.getValue2(), "#980680"),
				new JsonBeanHelper("Admins", dao.getValue1(), "#065535"),
				new JsonBeanHelper("Users", dao.getValue3(), "#41a973"),
				new JsonBeanHelper("Total", dao.getTotal(), "#113367")));
	}
	
	private List<JsonBeanHelper> generateVacationSortedData(BeanJsonHelper dao) {
		return new ArrayList<>(Arrays.asList(
				new JsonBeanHelper("Accepted", dao.getValue1(), "#41a973"),
				new JsonBeanHelper("Pending", dao.getValue2(), "#dfc731"),
				new JsonBeanHelper("Declined", dao.getValue3(), "#B91919")));
	}
	
	private List<JsonBeanHelper> generateAccountStatus(BeanJsonHelper dao) {
		return new ArrayList<>(Arrays.asList(
				new JsonBeanHelper("Activated", dao.getValue1(), "#41a973"),
				new JsonBeanHelper("NotActivated", dao.getValue2(), "#dfc731"),
				new JsonBeanHelper("Total", dao.getTotal(), "#113367")));
	}

}
