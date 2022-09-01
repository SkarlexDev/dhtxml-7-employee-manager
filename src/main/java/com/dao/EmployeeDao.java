package com.dao;

import java.util.List;
import java.util.Optional;

import com.bean.Employee;

public interface EmployeeDao {

	Optional<Employee> findByEmail(String email);

	Optional<Employee> findByEmpID(Long id);

	Optional<Employee> findByLoginAndPassword(String login, String password);

	List<Employee> findAll();

	boolean save(Employee bean);

	boolean update(Employee bean);

	void updateEmployeeVacationDays(Long result, Long id);

	Long checkRemainingDays(Long id);

	boolean delete(Long id);

	boolean activate(Employee bean, String key);

	boolean findByKey(String key);

	boolean addRole(long id);

	boolean removeRole(long id);

}
