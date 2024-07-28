package vn.edu.likelion.warehouse.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private String url;
    private String user;
    private String pass;
    private Connection conn = null;

    public ConnectDB() {
        url = "jdbc:oracle:thin:@localhost:1521:xe";
        user = "class3";
        pass = "123456";
    }

    public Connection openConnect() throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }

    public void closeConnect() throws SQLException {
        if (conn != null) conn.close();
    }

    public Connection getConnect() {
        return conn;
    }
}