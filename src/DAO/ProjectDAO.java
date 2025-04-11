package DAO;

import java.util.ArrayList;
import java.util.List;

import model.Project;
import util.FileHandler;

// ProjectDAO.java
public class ProjectDAO {
    private static final String PROJECT_FILE = "resources/data/projects.dat";

    @SuppressWarnings("unchecked")
    public static List<Project> getAllProjects() {
        List<Project> projects = (List<Project>) FileHandler.readObjectFromFile(PROJECT_FILE);
        return projects != null ? projects : new ArrayList<>();
    }

    public static List<Project> getProjectsByUserId(int userId) {
        List<Project> allProjects = getAllProjects();
        List<Project> userProjects = new ArrayList<>();

        for (Project project : allProjects) {
            if (project.getUserId() == userId) {
                userProjects.add(project);
            }
        }

        return userProjects;
    }

    public static Project getProjectById(int projectId) {
        List<Project> projects = getAllProjects();
        return projects.stream()
                .filter(p -> p.getId() == projectId)
                .findFirst()
                .orElse(null);
    }

    public static void saveProject(Project project) {
        List<Project> projects = getAllProjects();
        // Check if project exists already
        projects.removeIf(p -> p.getId() == project.getId());
        projects.add(project);
        saveAllProjects(projects);
    }

    public static int getNextProjectId() {
        return getAllProjects().size() + 1;
    }

    public static void saveAllProjects(List<Project> projects) {
        FileHandler.writeObjectToFile(PROJECT_FILE, projects);
    }

    public static String getProjectNameById(int projectId) {
        Project project = getProjectById(projectId);
        return project != null ? project.getName() : "Unknown Project";
    }
}
