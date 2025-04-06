package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String deadline;
    private String status;
    private ArrayList<Task> tasks;

    public Project(int id, String name, String deadline, String status) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.status = status;
        this.tasks = new ArrayList<>();
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
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public void addTask(Task task) {
        tasks.add(task);
    }
    public void removeTask(Task task) {
        tasks.remove(task);
    }
    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                return;
            }
        }
    }
    public void clearTasks() {
        tasks.clear();
    }
    public int getTaskCount() {
        return tasks.size();
    }
    public boolean hasTasks() {
        return !tasks.isEmpty();
    }
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    @Override
    public String toString() {
        return id + "|" + name + "|" + deadline + "|" + status;
    }

}
