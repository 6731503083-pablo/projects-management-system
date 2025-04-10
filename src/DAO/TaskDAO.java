package DAO;

import java.util.ArrayList;
import java.util.List;

import model.Task;
import util.FileHandler;

// TaskDAO.java
public class TaskDAO {
    private static final String TASK_FILE = "resources/data/tasks.dat";

    @SuppressWarnings("unchecked")
    public static List<Task> getAllTasks() {
        List<Task> tasks = (List<Task>) FileHandler.readObjectFromFile(TASK_FILE);
        return tasks != null ? tasks : new ArrayList<>();
    }

    public static List<Task> getTasksByProjectId(int projectId) {
        List<Task> allTasks = getAllTasks();
        List<Task> projectTasks = new ArrayList<>();

        for (Task task : allTasks) {
            if (task.getProjectId() == projectId) {
                projectTasks.add(task);
            }
        }

        return projectTasks;
    }

    public static List<Task> getTasksByUserId(int userId) {
        List<Task> allTasks = getAllTasks();
        List<Task> userTasks = new ArrayList<>();

        for (Task task : allTasks) {
            if (task.getUserId() == userId) {
                userTasks.add(task);
            }
        }

        return userTasks;
    }

    public static Task getTaskById(int taskId) {
        List<Task> tasks = getAllTasks();
        return tasks.stream()
                .filter(t -> t.getId() == taskId)
                .findFirst()
                .orElse(null);
    }

    public static void saveTask(Task task) {
        List<Task> tasks = getAllTasks();
        // Check if task exists already
        tasks.removeIf(t -> t.getId() == task.getId());
        tasks.add(task);
        saveAllTasks(tasks);
    }

    public static int getNextTaskId() {
        return getAllTasks().size() + 1;
    }

    public static void saveAllTasks(List<Task> tasks) {
        FileHandler.writeObjectToFile(TASK_FILE, tasks);
    }
}