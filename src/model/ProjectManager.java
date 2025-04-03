package model;

import java.util.ArrayList;

import model.enums.UserRoles;

public class ProjectManager extends User{
     private ArrayList <Project> projects;

        public ProjectManager(int id, String name, String email,UserRoles role) {
            super(id, name, email, role);
            this.projects = new ArrayList<>();
        }

        public ArrayList<Project> getProjects() {
            return projects;
        }

        public void createProject(String name, String deadline) {
            int id = projects.size() + 1; // Simple ID generation
            Project project = new Project(id, name, deadline, "New");
            projects.add(project);
        }

        public ArrayList<Project> viewProjects() {
            return projects;
        }

        public void updateProject(int projectId, String newName, String newDeadline) {
            for (Project project : projects) {
                if (project.getId() == projectId) {
                    project.setName(newName);
                    project.setDeadline(newDeadline);
                    break;
                }
            }
        }

        public boolean deleteProject(int projectId) {
            for (Project project : projects) {
                if (project.getId() == projectId) {
                    projects.remove(project);
                    return true;
                }
            }
            return false;
        }
        public void assignTaskToProject(Task task, Project project) {
            for (Project p : projects) {
                if (p.getId() == project.getId()) {
                    p.addTask(task);
                    break;
                }
            }
        }
        public void removeTaskFromProject(Task task, Project project) {
            for (Project p : projects) {
                if (p.getId() == project.getId()) {
                    p.removeTask(task);
                    break;
                }
            }
        }
        public void clearProjects() {
            projects.clear();
        }
        public int getProjectCount() {
            return projects.size();
        }
        public boolean hasProjects() {
            return !projects.isEmpty();
        }

}
