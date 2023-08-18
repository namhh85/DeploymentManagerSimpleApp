/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.controller;

import app.dao.StageDao;
import app.model.DeploymentInfo;
import app.model.Stage;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class StageController {
    private StageDao stageDao;
    
    public StageController() {
        stageDao = new StageDao();
    }
    
    public List<Stage> search(int deploymentProfileId, String name, String description) {
        return stageDao.search(deploymentProfileId, name, description);
    }

    public boolean insert(DeploymentInfo deploymentInfo, String name, String description) {
        Stage stage = new Stage();
        stage.setName(name);
        stage.setDescription(description);
        stage.setDeploymentInfo(deploymentInfo);
        return stageDao.insert(stage);
    }

    public boolean update(DeploymentInfo deploymentInfo, String name, String description) {
        Stage stage = new Stage();
        stage.setName(name);
        stage.setDescription(description);
        stage.setDeploymentInfo(deploymentInfo);
        return stageDao.update(stage);
    }

    public boolean delete(DeploymentInfo deploymentInfo, Stage stage) {
        return stageDao.delete(deploymentInfo.getId(), stage.getId());
    }
}
