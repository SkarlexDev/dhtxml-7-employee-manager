package com.dao.impl;

import com.bean.Vacation;
import com.dao.VacationDao;
import com.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class VacationDaoImpl implements VacationDao {

	private static final Logger log = Logger.getLogger(VacationDaoImpl.class.getName());

	@Override
	public Optional<Vacation> findById(Long id) {
		log.info("Getting vacation info");
		String sql = "select * from projecta.vacation where id = ?";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs= null;
		try {
            conn = DbUtil.getConnection();
            st = conn.prepareStatement(sql);
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                return Optional.of(createVacation(rs));
            }
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        } finally {
            DbUtil.closeConn(rs, st, conn);
        }
        return Optional.empty();
	}

	@Override
	public List<Vacation> findAll() {
		log.info("Request to find all Vacation");
		String sql = "select * from projecta.vacation";
		List<Vacation> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				list.add(createVacation(rs));
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
		String sql = "select * from projecta.vacation where employee_id = ?";
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
				list.add(createVacation(rs));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
		return list;
	}

	@Override
	public List<Vacation> findAllByEmployeeWithPending(Long id) {
		log.info("Request to find all Vacation by emp id with pending status");
		String sql = "select * from projecta.vacation where employee_id = ? and status ='Pending'";
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
				list.add(createVacation(rs));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
		return list;
	}

	@Override
	public boolean save(Vacation bean) {
		log.info("Request save Employee Vacation");
		String sql = "insert into projecta.vacation (starting, ending, reason, status, employee_id) Values (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setDate(1, Date.valueOf(bean.getVacationFrom()));
			st.setDate(2, Date.valueOf(bean.getVacationTo()));
			st.setString(3, bean.getReason());
			st.setString(4, "Pending");
			st.setLong(5, bean.getEmployeeId());
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
		String sql = "insert into projecta.vacation (starting, ending, reason, status, employee_id) Values (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			for (Vacation bean : list) {
				st.setDate(1, Date.valueOf(bean.getVacationFrom()));
				st.setDate(2, Date.valueOf(bean.getVacationTo()));
				st.setString(3, bean.getReason());
				st.setString(4, bean.getStatus());
				st.setLong(5, bean.getEmployeeId());
				st.addBatch();
			}
			int [] execute = st.executeBatch();
			for (int j : execute) {
				if (j == 0) {
					return false;
				}
			}
			return true;
		} catch (ClassNotFoundException | SQLException e1) {
			log.info("Failed to insert data!");
			return false;
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
	}

	@Override
	public boolean update(Vacation bean) {
		log.info("Update vacation data");
		String sql = "update projecta.vacation set starting = ?, ending = ?, reason = ? where id = ?";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setDate(1, Date.valueOf(bean.getVacationFrom()));
			st.setDate(2, Date.valueOf(bean.getVacationTo()));
			st.setString(3, bean.getReason());
			st.setLong(4, bean.getId());
			return st.executeUpdate() > 0;
		} catch (ClassNotFoundException | SQLException e1) {
			log.info("Failed to update data!");
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
		return false;
	}

	@Override
	public void handleVacationStatus(String status, Long id) {
		log.info("Update vacation status");
		String sql = "update projecta.vacation set status = ? where id = ?";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, status);
			st.setLong(2, id);
			st.executeUpdate();
		} catch (ClassNotFoundException | SQLException e1) {
			log.info("Failed to update data!");
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
		
	}
	
	private Vacation createVacation(ResultSet rs) throws SQLException {
		Vacation bean = new Vacation();
		bean.setId(rs.getLong(1));
		bean.setVacationFrom(rs.getDate(2).toLocalDate());
		bean.setVacationTo(rs.getDate(3).toLocalDate());
		bean.setReason(rs.getString(4));
		bean.setStatus(rs.getString(5));
		bean.setEmployeeId(rs.getLong(6));
		return bean;
	}
	
}
