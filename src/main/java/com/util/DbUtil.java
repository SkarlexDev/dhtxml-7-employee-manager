package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DbUtil {

    private final static Logger log = Logger.getLogger(DbUtil.class.getName());

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        log.info("Requesting db connection...");
        Class.forName("org.postgresql.Driver");
        String password = "1234";
        String user = "postgres";
        log.info("Connection successful");
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeConn(ResultSet rs, PreparedStatement stmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
                log.info("ResultSet closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
                log.info("Statement closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
                log.info("Connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
