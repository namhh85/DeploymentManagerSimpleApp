/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.controller;

import app.dao.DeploymentInfoDao;
import app.model.DeploymentInfo;
import app.model.User;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class DeploymentInfoController {
    private DeploymentInfoDao deploymentInfoDao;

    public DeploymentInfoController() {
        this.deploymentInfoDao = new DeploymentInfoDao();
    }
    
    public List<DeploymentInfo> search(String name, String description, List<Integer> statusList, Date startDate, Date endDate) {
        return deploymentInfoDao.search(name, description, startDate, endDate, statusList);
    }
    
    public boolean insert(User user, String name, String description, Integer status, Date startDate, Date endDate) {
        DeploymentInfo deploymentInfo = new DeploymentInfo(user, name, description, startDate, endDate, status, new Timestamp(System.currentTimeMillis()));
        return deploymentInfoDao.insert(deploymentInfo);
    }
    
    public boolean update(int id, String name, String description, Integer status, Date startDate, Date endDate) {
        DeploymentInfo deploymentInfo = new DeploymentInfo(id, name, description, startDate, endDate, status);
        return deploymentInfoDao.update(deploymentInfo);
    }
    
    public boolean delete(int id) {
        return deploymentInfoDao.delete(id);
    }
}
