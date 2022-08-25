package com.dao;

import com.bean.Vacation;

import java.util.List;
import java.util.Optional;

public interface VacationDao {

	Optional<Vacation> findById(Long id);
	
	List<Vacation> findAll();

	List<Vacation> findAllByEmployee(Long id);

	List<Vacation> findAllByEmployeeWithPending(Long id);
	
	boolean save(Vacation bean);

	boolean saveAll(List<Vacation> list);
	
	boolean update(Vacation bean);
	
	void handleVacationStatus(String status, Long id);

}
