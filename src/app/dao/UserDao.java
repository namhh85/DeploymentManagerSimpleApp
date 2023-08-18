/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ADMIN
 */
public class UserDao extends BaseDao {
    public User getUserByUsername(String username) {
        Connection connection = null;
        PreparedStatement ps = null;
        User user = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM user ");
            sql.append("WHERE username = ? ");
            ps = connection.prepareStatement(sql.toString());
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               int id = rs.getInt("id");
               String name = rs.getString("name");
               String loginName = rs.getString("username");
               String password = rs.getString("password");
               user = new User(id, name, loginName, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return user;
    }
}
