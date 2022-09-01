package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bean.jsonhelper.BeanJsonHelper;
import com.dao.DataDao;
import com.util.DbUtil;

public class DataDaoImpl implements DataDao {

	@Override
	public BeanJsonHelper findAllEmployee() {
		String sql = "select "
				+ "(select count(*) as Admins from projecta.employee e "
				+ "INNER join projecta.employee_role er on er.emp_id = e.id "
				+ "INNER join projecta.role r on r.id = er.role_id where r.name = 'Admin'), "
				+ "(select count(*) as Users from projecta.employee e "
				+ "INNER join projecta.employee_role er on er.emp_id = e.id "
				+ "INNER join projecta.role r on r.id = er.role_id where r.name = 'User'), "
				+ "(select count(*) as Creator from projecta.employee e "
				+ "INNER join projecta.employee_role er on er.emp_id = e.id "
				+ "INNER join projecta.role r on r.id = er.role_id where r.name = 'Creator')";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		BeanJsonHelper bean = new BeanJsonHelper();
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				bean.setValue1(rs.getLong(1));
				bean.setTotal(rs.getLong(2));
				bean.setValue2(rs.getLong(3));
				bean.setEmployeeTotalUsers();
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(rs, st, conn);
		}
		return bean;
	}

	@Override
	public BeanJsonHelper findAllVacation() {
		String sql = "select "
				+ "(select count(*) as Accepted from projecta.vacation v WHERE v.status = 'Accepted'), "
				+ "(select count(*) as Pending from projecta.vacation v WHERE v.status = 'Pending'), "
				+ "(select count(*) as Declined from projecta.vacation v WHERE v.status = 'Declined')";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		BeanJsonHelper bean = new BeanJsonHelper();
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				bean.setValue1(rs.getLong(1));
				bean.setValue2(rs.getLong(2));
				bean.setValue3(rs.getLong(3));
				bean.setVacationsTotal();
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(rs, st, conn);
		}
		return bean;
	}

	@Override
	public BeanJsonHelper findAllAccountsStatus() {
		String sql = "select (select count(*) as activated from projecta.employee e where e.activated = true), (select count(*) as notactivated from projecta.employee e where e.activated = false)";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		BeanJsonHelper bean = new BeanJsonHelper();
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				bean.setValue1(rs.getLong(1));
				bean.setValue2(rs.getLong(2));
				bean.setAccountsTotal();
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(rs, st, conn);
		}
		return bean;
	}

}
