package com.dao;

import java.util.List;

import com.bean.Vacation;

public interface VacationDao {

    List<Vacation> findAllByEmployee(Long id);

    boolean save(Vacation bean);

}
