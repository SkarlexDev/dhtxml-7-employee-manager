package com.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import com.bean.Employee;
import com.bean.Role;
import com.dao.EmployeeDao;
import com.util.DbUtil;

public class EmployeeDaoImpl implements EmployeeDao {

	private static final Logger log = Logger.getLogger(EmployeeDaoImpl.class.getName());

	@Override
	public Optional<Employee> findByEmail(String email) {
		log.info("Request to Employee by email");
		String sql = "select * from projecta.employee where email = ?";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, email);
			rs = st.executeQuery();
			if (rs.next()) {
				return Optional.of(createEmployee(rs, conn));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(rs, st, conn);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Employee> findByEmpID(Long id) {
		log.info("Request to find Employee");
		String sql = "select * from projecta.employee where id = ?";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				return Optional.of(createEmployee(rs, conn));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(rs, st, conn);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Employee> findByLoginAndPassword(String login, String password) {
		log.info("Request to find User by name and password");
		String sql = "select * from projecta.employee where email = ? and password = ?";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Optional<Employee> bean = Optional.empty();
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, login);
			st.setString(2, password);
			rs = st.executeQuery();
			if (rs.next()) {
				bean = Optional.of(createEmployee(rs, conn));
			}
			return bean;
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(rs, st, conn);
		}
		return Optional.empty();
	}

	@Override
	public List<Employee> findAll() {
		log.info("Request to find all Employee");
		String sql = "select * from projecta.employee";
		List<Employee> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				list.add(createEmployee(rs, conn));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
		return list;
	}

	@Override
	public boolean save(Employee bean) {
		log.info("Request save Employee");
		if (findByEmail(bean.getEmail()).isPresent()) {
			return false;
		}
		String sql = "insert into projecta.employee (name, phone, email, birth_date, address, country, password, vacations) "
				+ "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, bean.getName());
			st.setString(2, bean.getPhone());
			st.setString(3, bean.getEmail());
			st.setDate(4, Date.valueOf(bean.getBirthDate()));
			st.setString(5, bean.getAddress());
			st.setString(6, bean.getCountry());
			st.setString(7, "123");
			st.setLong(8, 50);
			if (st.executeUpdate() > 0) {
				String role = "insert into projecta.employee_role (emp_id , role_id) Values ((select e.id from projecta.employee e where e.email = ?), (select r.id from projecta.role r where r.name ='User'))";
				PreparedStatement sts = conn.prepareStatement(role);
				sts.setString(1, bean.getEmail());
				int update = sts.executeUpdate();
				sts.close();
				return update > 0;
			}
			return false;
		} catch (ClassNotFoundException | SQLException e1) {
			log.info("Failed to insert data!");
			return false;
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
	}

	@Override
	public boolean update(Employee bean) {
		log.info("Request update Employee");
		String sql = "update projecta.employee set name= ? , phone= ?, email= ?, birth_date= ?, address= ?, country= ? where id = ?";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, bean.getName());
			st.setString(2, bean.getPhone());
			st.setString(3, bean.getEmail());
			st.setDate(4, Date.valueOf(bean.getBirthDate()));
			st.setString(5, bean.getAddress());
			st.setString(6, bean.getCountry());
			st.setLong(7, bean.getId());
			return st.executeUpdate() > 0;
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
			return false;
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
	}

	@Override
	public void updateEmployeeVacationDays(Long result, Long id) {
		log.info("Updating employee vacation days");
		String sql = "update projecta.employee set vacations = ? where id = ?";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, result);
			st.setLong(2, id);
			st.executeUpdate();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		} finally {
			DbUtil.closeConn(null, st, conn);
		}		
	}

	@Override
	public Long checkRemainingDays(Long id) {
		log.info("Getting employee vacation days");
		String sql = "select e.vacations from projecta.employee e inner join projecta.vacation v on v.employee_id = e.id where v.id = ?";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs= null;
		Long days = 0L;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, id);
			rs = st.executeQuery();
			while(rs.next()) {
				days = rs.getLong(1);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			log.info("Failed to update data!");
		} finally {
			DbUtil.closeConn(rs, st, conn);
		}
		return days;
	}

	@Override
	public boolean delete(Long id) {
		log.info("Request to delete Employee");
		String sql = "delete from projecta.employee where id = ?";
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DbUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, id);
			return st.executeUpdate() > 0;
		} catch (ClassNotFoundException | SQLException e1) {
			log.info("No key found");
			return false;
		} finally {
			DbUtil.closeConn(null, st, conn);
		}
	}
	
	private Employee createEmployee(ResultSet rs, Connection conn) throws SQLException {
		Employee bean = new Employee();
		bean.setId(rs.getLong(1));
		bean.setName(rs.getString(2));
		bean.setPhone(rs.getString(3));
		bean.setEmail(rs.getString(4));
		bean.setVacations(rs.getLong(6));
		bean.setBirthDate(rs.getDate(7).toLocalDate());
		bean.setAddress(rs.getString(8));
		bean.setCountry(rs.getString(9));
		bean.setRoles(createRoles(rs.getLong(1), conn));
		return bean;
	}

	private Set<Role> createRoles(long id, Connection conn) throws SQLException {
		LinkedHashSet<Role> roles = new LinkedHashSet<>();
		String sqlRole = "select r.name from projecta.role r inner join projecta.employee_role er on er.role_id = r.id inner join projecta.employee e on er.emp_id = e.id where e.id = ?";
		PreparedStatement st = conn.prepareStatement(sqlRole);
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			roles.add(createRole(rs));
		}
		st.close();
		rs.close();
		return roles;
	}
	
	private Role createRole(ResultSet rs) throws SQLException {
		Role bean = new Role();
		bean.setName(rs.getString(1));
		return bean;
	}
}
