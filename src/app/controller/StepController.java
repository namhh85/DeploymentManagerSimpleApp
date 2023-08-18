/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.controller;

import app.dao.StepDao;
import app.model.DeploymentInfo;
import app.model.Stage;
import app.model.Step;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class StepController {
    private StepDao stepDao;
    
    public StepController() {
        stepDao = new StepDao();
    }

    public boolean insert(Step step) {
        return stepDao.insert(step);
    }

    public List<Step> findAll(DeploymentInfo deploymentInfo, Stage stage) {
        return stepDao.search(deploymentInfo, stage);
    }

    public boolean update(Step step) {
        return stepDao.update(step);
    }
    
    public int getNextId() {
        int lastId = stepDao.getLastId();
        return lastId + 1;
    }

    public boolean delete(Step step) {
        return stepDao.delete(step);
    }
}
