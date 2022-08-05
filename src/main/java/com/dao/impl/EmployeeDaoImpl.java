package com.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.bean.Employee;
import com.dao.EmployeeDao;
import com.util.DbUtil;

public class EmployeeDaoImpl implements EmployeeDao {

    private static final Logger log = Logger.getLogger(EmployeeDaoImpl.class.getName());

    @Override
    public List<Employee> findAll() {
        log.info("Request to find all Employee");
        String sql = "SELECT * FROM projecta.employee";
        List<Employee> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            conn = DbUtil.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                list.add(createEmployee(rs));
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
        String sql = "INSERT INTO PROJECTA.EMPLOYEE (\"name\", PHONE, email, birth_date, address, country) " + "VALUES "
                + "(?, ?, ?, ?, ?, ?)";
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
            return st.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e1) {
            log.info("Failed to insert data!");
            return false;
        } finally {
            DbUtil.closeConn(null, st, conn);
        }
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        log.info("Request to Employee by email");
        String sql = "SELECT * FROM PROJECTA.EMPLOYEE WHERE EMAIL = ?";
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, email);
            rs = st.executeQuery();
            if (rs.next()) {
                return Optional.of(createEmployee(rs));
            }
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        } finally {
            DbUtil.closeConn(rs, st, conn);
        }
        return Optional.empty();
    }

    private Employee createEmployee(ResultSet rs) throws SQLException {
        Employee bean = new Employee();
        bean.setId(rs.getLong(1));
        bean.setName(rs.getString(2));
        bean.setPhone(rs.getString(3));
        bean.setEmail(rs.getString(4));
        bean.setBirthDate(rs.getDate(5).toLocalDate());
        bean.setAddress(rs.getString(6));
        bean.setCountry(rs.getString(7));
        return bean;
    }

    @Override
    public boolean delete(Long id) {
        log.info("Request to delete Employee");
        String sql = "Delete from PROJECTA.EMPLOYEE where id = ?";
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

    @Override
    public boolean update(Employee bean) {
        log.info("Request update Employee");
        String sql = "Update PROJECTA.EMPLOYEE set \"name\"= ? , PHONE= ?, email= ?, birth_date= ?, address= ?, country= ? where id = ?";
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
}
