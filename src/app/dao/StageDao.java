/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.model.DeploymentInfo;
import app.model.Stage;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class StageDao extends BaseDao {
    public List<Stage> search(int deploymentProfileId, String name, String description) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<Stage> stageList = new ArrayList<>();
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("SELECT a.* FROM stage a INNER JOIN deployment_profile b ON a.deployment_profile_id = b.id ");
            sql.append("WHERE b.id = ? ");
            if (name != null && name != "") {
                sql.append("AND a.name LIKE ? ");
            }
            if (description != null && description != "") {
                sql.append("AND a.description LIKE ? ");
            }
            sql.append("ORDER BY a.id ASC ");
            ps = connection.prepareStatement(sql.toString());
            int index = 1;
            ps.setInt(index++, deploymentProfileId);
            if (name != null && name != "") {
                ps.setString(index++, "%" + name + "%");
            }
            if (description != null && description != "") {
                ps.setString(index++, "%" + description + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int deployProfileId = rs.getInt("deployment_profile_id");
                DeploymentInfo deploymentInfo = new DeploymentInfo();
                deploymentInfo.setId(deployProfileId);
                int stageId = rs.getInt("id");
                String stageName = rs.getString("name");
                String stageDescription = rs.getString("description");
                stageList.add(new Stage(stageId, deploymentInfo, stageName, stageDescription));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return stageList;
    }
    
    public boolean insert(Stage stage) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("INSERT INTO stage(deployment_profile_id, name, description) VALUES (?, ?, ?) ");
            ps = connection.prepareStatement(sql.toString());
            ps.setInt(1, stage.getDeploymentInfo().getId());
            ps.setString(2, stage.getName());
            ps.setString(3, stage.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return false;
    }
    
    public boolean update(Stage stage) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("UPDATE stage SET name = ?, description = ? WHERE id = ? AND deployment_profile_id = ? ");
            ps = connection.prepareStatement(sql.toString());
            ps.setString(1, stage.getName());
            ps.setString(2, stage.getDescription());
            ps.setInt(3, stage.getId());
            ps.setInt(4, stage.getDeploymentInfo().getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return false;
    }
    
    public boolean delete(int deploymentProfileId, int stageId) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("DELETE FROM stage WHERE id = ? AND deployment_profile_id = ? ");
            ps = connection.prepareStatement(sql.toString());
            ps.setInt(1, stageId);
            ps.setInt(2, deploymentProfileId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return false;
    }
}
