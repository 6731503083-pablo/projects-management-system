package Service;

import model.Project;
import DAO.ProjectDAO;
import util.Constants;

public class ProjectService {
    /**
     * Creates a new project with the given details
     * @param name The name of the project
     * @param deadline The deadline of the project
     * @param userId The ID of the user creating the project
     */
    public static void createProject(String name, String deadline, int userId) {
        int projectId = ProjectDAO.getNextProjectId();
        Project project = new Project(projectId, name, deadline, Constants.PROJECT_UNFINISHED, userId);
        ProjectDAO.saveProject(project);
    }
    
    /**
     * Marks a project as finished
     * @param projectId The ID of the project to mark as finished
     */
    public static void markProjectAsFinished(int projectId) {
        Project project = ProjectDAO.getProjectById(projectId);
        if (project != null) {
            project.setStatus(Constants.PROJECT_FINISHED);
            ProjectDAO.saveProject(project);
        }
    }
}