/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class DeploymentInfo {
    private int id;
    private User user;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private int status;
    private Timestamp createdAt;

    public DeploymentInfo() {
    }
    

    public DeploymentInfo(int id, User user, String name, String description, Date startTime, Date endTime, int status, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.createdAt = createdAt;
    }

    public DeploymentInfo(User user, String name, String description, Date startDate, Date endDate, Integer status, Timestamp createdAt) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.startTime = startDate;
        this.endTime = endDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public DeploymentInfo(String name, String description, Date startDate, Date endDate, Integer status) {
        this.name = name;
        this.description = description;
        this.startTime = startDate;
        this.endTime = endDate;
        this.status = status;
    }

    public DeploymentInfo(int id, String name, String description, Date startDate, Date endDate, Integer status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startDate;
        this.endTime = endDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
