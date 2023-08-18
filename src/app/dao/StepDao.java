/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.model.DeploymentInfo;
import app.model.Stage;
import app.model.Step;
import app.model.Tool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class StepDao extends BaseDao {
    public List<Step> search(DeploymentInfo deploymentInfo, Stage stage) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<Step> stepList = new ArrayList<>();
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("SELECT a.id as stepId, a.name as stepName, a.description as stepDescription, e.id as toolId, e.name as toolName, e.description as toolDescription FROM step a INNER JOIN stage b ON a.stage_id = b.id "
                    + "INNER JOIN deployment_profile c ON b.deployment_profile_id = c.id "
                    + "LEFT JOIN step_tool d ON a.id = d.step_id "
                    + "LEFT JOIN tool e ON d.tool_id = e.id ");
            sql.append("WHERE b.id = ? AND c.id = ? ");
            ps = connection.prepareStatement(sql.toString());
            int index = 1;
            ps.setInt(index++, stage.getId());
            ps.setInt(index++, deploymentInfo.getId());
            ResultSet rs = ps.executeQuery();
            List<Tool> toolList = new ArrayList<>();
            boolean isFirst = true;
            while (rs.next()) {
                int stepId = rs.getInt("stepId");
                String stepName = rs.getString("stepName");
                String stepDescription = rs.getString("stepDescription");
                Step step = new Step(stepId, stage, stepName, stepDescription);
                boolean isExist = checkExistStep(step, stepList);
                if (!isExist) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        Step beforeStep = stepList.get(stepList.size() - 1);
                        beforeStep.setToolList(toolList);
                        toolList = new ArrayList<>();
                    }
                    stepList.add(step);
                }
                int toolId = rs.getInt("toolId");
                if (toolId != 0) {
                    String toolName = rs.getString("toolName");
                    String toolDescription = rs.getString("toolDescription");
                    Tool tool = new Tool(toolId, toolName, toolDescription);
                    toolList.add(tool);
                }
                if (rs.isLast()) {
                    Step beforeStep = stepList.get(stepList.size() - 1);
                    beforeStep.setToolList(toolList);
                    toolList = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return stepList;
    }
    
    public boolean insert(Step step) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            
            StringBuilder sql = new StringBuilder("INSERT INTO step(stage_id, name, description) VALUES (?, ?, ?) ");
            ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, step.getStage().getId());
            ps.setString(2, step.getName());
            ps.setString(3, step.getDescription());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int stepId = rs.getInt(1);
                List<Tool> toolList = step.getToolList();
                if (!toolList.isEmpty()) {
                    for (Tool tool : toolList) {
                        sql = new StringBuilder("INSERT INTO tool(name, description) VALUES (?, ?) ");
                        ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, tool.getName());
                        ps.setString(2, tool.getDescription());
                        ps.executeUpdate();
                        rs = ps.getGeneratedKeys();
                        if (rs.next()) {
                            int toolId = rs.getInt(1);
                            sql = new StringBuilder("INSERT INTO step_tool(step_id, tool_id) VALUES (?, ?) ");
                            ps = connection.prepareStatement(sql.toString());
                            ps.setInt(1, stepId);
                            ps.setInt(2, toolId);
                            ps.executeUpdate();
                        }
                    }
                }
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(StepDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return false;
    }
    
    public boolean update(Step step) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            
            StringBuilder sql = new StringBuilder("UPDATE step SET name = ?, description = ? WHERE id = ? AND stage_id = ? ");
            ps = connection.prepareStatement(sql.toString());
            ps.setString(1, step.getName());
            ps.setString(2, step.getDescription());
            ps.setInt(3, step.getId());
            ps.setInt(4, step.getStage().getId());
            ps.executeUpdate();
            List<Tool> toolList = step.getToolList();
            if (!toolList.isEmpty()) {
                for (Tool tool : toolList) {
                    sql = new StringBuilder("UPDATE tool SET name = ?, description = ? WHERE id = ? ");
                    ps = connection.prepareStatement(sql.toString());
                    ps.setString(1, tool.getName());
                    ps.setString(2, tool.getDescription());
                    ps.setInt(3, tool.getId());
                    int row = ps.executeUpdate();
                    if (row < 1) {
                        sql = new StringBuilder("INSERT INTO tool(name, description) VALUES(?, ?)");
                        ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, tool.getName());
                        ps.setString(2, tool.getDescription());
                        ps.executeUpdate();
                        ResultSet rs = ps.getGeneratedKeys();
                        if (rs.next()) {
                            int toolId = rs.getInt(1);
                            sql = new StringBuilder("INSERT INTO step_tool(step_id, tool_id) VALUES(?, ?) ");
                            ps = connection.prepareStatement(sql.toString());
                            ps.setInt(1, step.getId());
                            ps.setInt(2, toolId);
                            ps.executeUpdate();
                        }
                    }
                }
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(StepDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return false;
    }
    
    public boolean delete(Step step) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("DELETE FROM step WHERE id = ? AND stage_id = ? ");
            ps = connection.prepareStatement(sql.toString());
            ps.setInt(1, step.getId());
            ps.setInt(2, step.getStage().getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return false;
    }

    private boolean checkExistStep(Step step, List<Step> stepList) {
        for (Step s : stepList) {
            if (step.getId() == s.getId()) {
                return true;
            }
        }
        return false;
    }
    
    public int getLastId() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            StringBuilder sql = new StringBuilder("select id from tool order by id DESC LIMIT 1");
            ps = connection.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, null);
        }
        return 0;
    }
}
