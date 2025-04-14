package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String deadline;
    private String status;
    private ArrayList<Integer> tasksId;
    private int projectManagerId; // User ID of the project manager

    public Project(int id, String name, String deadline, String status, int userId) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.status = status;
        this.tasksId = new ArrayList<>();
        this.projectManagerId = userId;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Integer> getTasksId() {
        return tasksId;
    }

    public void setTasksId(ArrayList<Integer> tasksId) {
        this.tasksId = tasksId;
    }

    public int getProjectManagerId() {
        return projectManagerId;
    }
    public void setProjectManagerId(int userId) {
        this.projectManagerId = userId;
    }

    @Override
    public String toString() {
        return id + "|" + name + "|" + deadline + "|" + status + "|" + tasksId;
    }

}
