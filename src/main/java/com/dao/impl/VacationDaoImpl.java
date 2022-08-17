package com.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.bean.Vacation;
import com.dao.VacationDao;
import com.util.DbUtil;

public class VacationDaoImpl implements VacationDao {

	private static final Logger log = Logger.getLogger(VacationDaoImpl.class.getName());

	@Override
	public List<Vacation> findAll() {
		log.info("Request to find all Vacation");
		String sql = "SELECT * FROM projecta.vacation";
		List<Vacation> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				list.add(createVacantion(rs));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
		return list;
	}

	@Override
	public List<Vacation> findAllByEmployee(Long id) {
		log.info("Request to find all Vacation by emp id");
		String sql = "SELECT * FROM projecta.vacation where employee_id = ?";
		List<Vacation> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, id);
			rs = st.executeQuery();
			while (rs.next()) {
				list.add(createVacantion(rs));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
		return list;
	}

	private Vacation createVacantion(ResultSet rs) throws SQLException {
		Vacation bean = new Vacation();
		bean.setId(rs.getLong(1));
		bean.setVacationFrom(rs.getDate(2).toLocalDate());
		bean.setVacationTo(rs.getDate(3).toLocalDate());
		bean.setReason(rs.getString(4));
		bean.setEmployeeId(rs.getLong(5));
		return bean;
	}

	@Override
	public boolean save(Vacation bean) {
		log.info("Request save Employee Vacation");
		String sql = "insert into projecta.vacation (starting, ending, reason, employee_id) Values (?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setDate(1, Date.valueOf(bean.getVacationFrom()));
			st.setDate(2, Date.valueOf(bean.getVacationTo()));
			st.setString(3, bean.getReason());
			st.setLong(4, bean.getEmployeeId());
			return st.executeUpdate() > 0;
		} catch (ClassNotFoundException | SQLException e1) {
			log.info("Failed to insert data!");
			return false;
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
	}

	@Override
	public boolean saveAll(List<Vacation> list) {
		log.info("Request save Vacation list");
		String sql = "insert into projecta.vacation (starting, ending, reason, employee_id) Values (?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			for (Vacation bean : list) {
				st.setDate(1, Date.valueOf(bean.getVacationFrom()));
				st.setDate(2, Date.valueOf(bean.getVacationTo()));
				st.setString(3, bean.getReason());
				st.setLong(4, bean.getEmployeeId());
				st.addBatch();
			}
			st.executeBatch();
			return true;
		} catch (ClassNotFoundException | SQLException e1) {
			log.info("Failed to insert data!");
			return false;
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
	}
}
