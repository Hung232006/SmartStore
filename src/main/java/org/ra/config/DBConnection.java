package org.ra.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final Properties PROPS = new Properties();

    static {
        try {
            InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
            if (in == null) {
                throw new RuntimeException("Khong thay db.propertis");
            }
            PROPS.load(in);
            Class.forName("org.postgresql.Driver");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {

        try {

            Connection conn = DriverManager.getConnection(PROPS.getProperty("db.url"),PROPS.getProperty("db.username"),PROPS.getProperty("db.password"));
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {}
    }

}
