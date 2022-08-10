package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

import com.bean.User;
import com.dao.UserDao;
import com.util.DbUtil;

public class UserDaoImpl implements UserDao {

    private static final Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    @Override
    public Optional<User> findByNameAndPassword(String userName, String password) {
        log.info("Request to find User by name and password");
        String sql = "select * from projecta.\"user\" where user_name = ? and \"password\" = ?";
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, userName);
            st.setString(2, password);
            rs = st.executeQuery();
            if (rs.next()) {
                return Optional.of(createUser(rs));
            }
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        } finally {
            DbUtil.closeConn(rs, st, conn);
        }
        return Optional.empty();
    }

    private User createUser(ResultSet rs) throws SQLException {
        User bean = new User();
        bean.setId(rs.getLong(1));
        bean.setUserName(rs.getString(2));
        bean.setEmp_id(rs.getLong(4));
        return bean;
    }

}
