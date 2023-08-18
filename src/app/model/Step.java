/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Step {
    private int id;
    private Stage stage;
    private String name;
    private String description;
    private List<Tool> toolList;
    
    public Step() {
        
    }
    
    public Step(Stage stage, String name, String description) {
        this.stage = stage;
        this.name = name;
        this.description = description;
    }

    public Step(int id, Stage stage, String name, String description) {
        this.id = id;
        this.stage = stage;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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

    public List<Tool> getToolList() {
        return toolList;
    }

    public void setToolList(List<Tool> toolList) {
        this.toolList = toolList;
    }

}
