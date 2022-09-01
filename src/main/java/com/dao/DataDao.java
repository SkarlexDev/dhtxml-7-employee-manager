package com.dao;

import com.bean.jsonhelper.BeanJsonHelper;

public interface DataDao {

	BeanJsonHelper findAllEmployee();

	BeanJsonHelper findAllVacation();
    
	BeanJsonHelper findAllAccountsStatus();

}
