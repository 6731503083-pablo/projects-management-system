package model;

import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String description;
    private String status;
    private int projectId;
    private int devloperId; // User ID of the developer assigned to the task

    public Task(int id, String name, String description, String status, int projectId, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.projectId = projectId;
        this.devloperId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateTaskStatus(String status) {
        this.status = status;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getDevloperId() {
        return devloperId;
    }

    public void setDevloperId(int userId) {
        this.devloperId = userId;
    }

    @Override
    public String toString() {
        return id + "|" + name + "|" + description + "|" + status + "|" + projectId;
    }

}