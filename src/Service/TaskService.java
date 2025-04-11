package Service;

import model.Task;
import DAO.TaskDAO;
import util.Constants;

public class TaskService {
    /**
     * Creates a new task with the given details
     * @param name The name of the task
     * @param description The description of the task
     * @param projectId The ID of the project this task belongs to
     * @param developerId The ID of the developer assigned to the task
     */
    public static void createTask(String name, String description, int projectId, int developerId) {
        int taskId = TaskDAO.getNextTaskId();
        Task task = new Task(taskId, name, description, Constants.STATUS_TODO, projectId, developerId);
        TaskDAO.saveTask(task);
    }
    
    /**
     * Updates the status of a task
     * @param taskId The ID of the task to update
     * @param newStatus The new status of the task
     */
    public static void updateTaskStatus(int taskId, String newStatus) {
        Task task = TaskDAO.getTaskById(taskId);
        if (task != null) {
            task.setStatus(newStatus);
            TaskDAO.saveTask(task);
        }
    }
}
