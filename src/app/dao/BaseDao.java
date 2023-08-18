/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class BaseDao {
    //Tên database qldeploy, host: localhost, port: 3306
    private static final String DB_URL = "jdbc:mysql://localhost:3306/qldeploy?serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "";

    public Connection getConnection() {
        try {
                return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
                System.out.println("Lỗi khởi tạo connection " + e);
        }
        return null;
    }

    public void closeConnection(Connection connection, PreparedStatement ps, Statement s) {
        try {
                if (connection != null) {
                        connection.close();
                }
                if (ps != null) {
                        ps.close();
                }
                if (s != null) {
                        s.close();
                }
        } catch (Exception e) {
                System.out.println("Lỗi close connection " + e);
        }
    }
}
