package com.dao;

import java.util.List;
import java.util.Optional;

import com.bean.Employee;

public interface EmployeeDao {

    List<Employee> findAll();

    Optional<Employee> findByEmail(String email);

    boolean save(Employee bean);

    boolean delete(Long id);

    boolean update(Employee bean);

    Optional<Employee> findByEmpID(Long id);
}
