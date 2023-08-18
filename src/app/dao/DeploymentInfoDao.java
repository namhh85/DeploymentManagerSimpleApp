/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.model.DeploymentInfo;
import app.model.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class DeploymentInfoDao extends BaseDao {
    public List<DeploymentInfo> search(String name, String description, Date startTime, Date endTime, List<Integer> statusList) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<DeploymentInfo> deploymentInfos = new ArrayList<>();
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("SELECT a.*, b.name as username FROM deployment_profile a INNER JOIN user b ON a.user_id = b.id ");
            sql.append("WHERE 1 = 1 ");
            if (name != null && name != "") {
                sql.append("AND a.name LIKE ? ");
            }
            if (description != null && description != "") {
                sql.append("AND a.description LIKE ? ");
            }
            if (startTime != null && endTime != null) {
                sql.append("AND ((a.start_time >= ? AND a.end_time <= ?) OR (a.start_time >= ? AND a.end_time <= ?)) ");
            }
            if (statusList != null) {
                sql.append("AND a.status IN (?, ?) ");
            }
            sql.append("ORDER BY a.created_at DESC ");
            ps = connection.prepareStatement(sql.toString());
            int index = 1;
            if (name != null && name != "") {
                ps.setString(index++, "%" + name + "%");
            }
            if (description != null && description != "") {
                ps.setString(index++, "%" + description + "%");
            }
            if (startTime != null && endTime != null) {
                ps.setDate(index++, new java.sql.Date(startTime.getTime()));
                ps.setDate(index++, new java.sql.Date(startTime.getTime()));
                ps.setDate(index++, new java.sql.Date(endTime.getTime()));
                ps.setDate(index++, new java.sql.Date(endTime.getTime()));
            }
            if (statusList != null) {
                ps.setInt(index++, statusList.get(0));
                ps.setInt(index++, statusList.get(1));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                int userId = rs.getInt("user_id");
                User user = new User(userId, username);
                String deployName = rs.getString("name");
                String deployDescription = rs.getString("description");
                int deployStatus = rs.getInt("status");
                Date deployStartTime = rs.getDate("start_time");
                Date deployEndTime = rs.getDate("end_time");
                Timestamp createdAt = rs.getTimestamp("created_at");
                deploymentInfos.add(new DeploymentInfo(id, user, deployName, deployDescription, deployStartTime, deployEndTime, deployStatus, createdAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return deploymentInfos;
    }
    
    public boolean insert(DeploymentInfo deploymentInfo) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("INSERT INTO deployment_profile(user_id, name, description, status, start_time, end_time, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps = connection.prepareStatement(sql.toString());
            ps.setInt(1, deploymentInfo.getUser().getId());
            ps.setString(2, deploymentInfo.getName());
            ps.setString(3, deploymentInfo.getDescription());
            ps.setInt(4, deploymentInfo.getStatus());
            ps.setDate(5, new java.sql.Date(deploymentInfo.getStartTime().getTime()));
            ps.setDate(6, new java.sql.Date(deploymentInfo.getEndTime().getTime()));
            ps.setTimestamp(7, deploymentInfo.getCreatedAt());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return false;
    }
    
    public boolean update(DeploymentInfo deploymentInfo) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("UPDATE deployment_profile SET name = ?, description = ?, status = ?, start_time = ?, end_time = ? WHERE id = ? ");
            ps = connection.prepareStatement(sql.toString());
            ps.setString(1, deploymentInfo.getName());
            ps.setString(2, deploymentInfo.getDescription());
            ps.setInt(3, deploymentInfo.getStatus());
            ps.setDate(4, new java.sql.Date(deploymentInfo.getStartTime().getTime()));
            ps.setDate(5, new java.sql.Date(deploymentInfo.getEndTime().getTime()));
            ps.setInt(6, deploymentInfo.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return false;
    }
    
    public boolean delete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("DELETE FROM deployment_profile WHERE id = ? ");
            ps = connection.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return false;
    }
}
