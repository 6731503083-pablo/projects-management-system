package Service;

import java.util.List;
import java.util.Scanner;

import DAO.ProjectDAO;
import DAO.TaskDAO;
import DAO.UserDAO;
import model.Project;
import model.Task;
import util.Constants;
import util.InputHandler;

public class MenuService {
    /**
     * Prompts the user for their email
     * @param scanner Scanner for reading input
     * @return The email entered by the user
     */
    public static String promptForUserEmail(Scanner scanner) {
        return InputHandler.getValidString(scanner, "Enter your email: ");
    }
    
    /**
     * Displays the main menu for project managers
     * @param scanner Scanner for reading input
     * @param userId The ID of the current user
     */
    public static void displayProjectManagerMenu(Scanner scanner, int userId) {
        while (true) {
            System.out.println("\n1. Create a new project");
            System.out.println("2. View all projects");
            System.out.println("3. Exit\n");
            
            int choice = InputHandler.getValidInteger(scanner, "Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createNewProject(scanner, userId);
                    break;
                case 2:
                    viewAllProjects(scanner, userId);
                    break;
                case 3:
                    System.out.println("\nExiting the program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Handles the creation of a new project
     * @param scanner Scanner for reading input
     * @param userId The ID of the current user
     */
    private static void createNewProject(Scanner scanner, int userId) {
        // Input project data
        String projectName = InputHandler.getValidString(scanner, "\nProject Name: ");
        String projectDeadline = InputHandler.getValidString(scanner, "\nProject Deadline (DD-MM-YYYY): ");
        
        // Create the project using the ProjectService
        ProjectService.createProject(projectName, projectDeadline, userId);
        
        System.out.println("\nProject created successfully!");
        pauseExecution(1000, "\nExiting back to the main menu...\n");
    }
    
    /**
     * Displays all projects for a project manager
     * @param scanner Scanner for reading input
     * @param userId The ID of the current user
     */
    private static void viewAllProjects(Scanner scanner, int userId) {
        List<Project> managerProjects = ProjectDAO.getProjectsByUserId(userId);
        
        if (managerProjects.isEmpty()) {
            System.out.println("\nNo projects found.\n");
            pauseExecution(1000, "\nExiting back to the main menu...\n");
            return;
        }
        
        // Display projects
        System.out.println("\nProject ID | Project Name   | Project Deadline  | Project Status");
        System.out.println("---------------------------------------------------------------");
        for (Project project : managerProjects) {
            System.out.printf("%-10d | %-14s | %-17s | %s%n",
                    project.getId(),
                    project.getName(),
                    project.getDeadline(),
                    project.getStatus());
        }
        
        // Get project selection
        int projectId = InputHandler.getValidInteger(scanner, 
                "\nEnter Project ID to view details (0 to go back): ");
        
        if (projectId == 0) {
            pauseExecution(1000, "\nExiting back to the main menu...\n");
            return;
        }
        
        // Handle project details view
        viewProjectDetails(scanner, projectId, userId);
    }
    
    /**
     * Displays detailed view of a project and options for actions
     * @param scanner Scanner for reading input
     * @param projectId The ID of the selected project
     * @param userId The ID of the current user
     */
    private static void viewProjectDetails(Scanner scanner, int projectId, int userId) {
        Project project = ProjectDAO.getProjectById(projectId);
        
        if (project == null) {
            System.out.println("Invalid Project ID. Please try again.");
            return;
        }
        
        // Verify project ownership
        if (project.getUserId() != userId) {
            System.out.println("\nThis project doesn't belong to you. Access denied.");
            pauseExecution(1000, "\nExiting back to the main menu...\n");
            return;
        }
        
        // Display project details
        System.out.println("\nProject ID: " + project.getId());
        System.out.println("\nProject Name: " + project.getName());
        System.out.println("\nProject Deadline: " + project.getDeadline());
        System.out.println("\nProject Status: " + project.getStatus());
        System.out.println("\n-----------------------------------------------------");
        
        boolean isFinished = project.getStatus().equalsIgnoreCase(Constants.PROJECT_FINISHED);
        if (isFinished) {
            System.out.println("This project is already finished. No further actions can be taken.");
            pauseExecution(1000, "\nExiting back to the main menu...\n");
            return;
        }
        
        // Display project options
        System.out.println("\n1. Mark project as finished\n");
        System.out.println("2. Create a task for this project\n");
        System.out.println("3. View tasks for this project\n");
        System.out.println("4. Go back to the main menu\n");
        
        int projectChoice = InputHandler.getValidInteger(scanner, "Your Choice: ");
        
        switch (projectChoice) {
            case 1:
                ProjectService.markProjectAsFinished(projectId);
                System.out.println("\nProject status updated to Finished!");
                pauseExecution(1000, "\nExiting back to the main menu...");
                break;
            case 2:
                createTaskForProject(scanner, project);
                break;
            case 3:
                viewTasksForProject(scanner, project);
                break;
            case 4:
                pauseExecution(1000, "\nExiting back to the main menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    /**
     * Creates a new task for a project
     * @param scanner Scanner for reading input
     * @param project The project to create a task for
     */
    private static void createTaskForProject(Scanner scanner, Project project) {
        System.out.println("\nCreating a task for Project Name: " + project.getName());
        
        // Get task details
        String taskName = InputHandler.getValidString(scanner, "Task Name: ");
        String taskDescription = InputHandler.getValidString(scanner, "Task Description: ");
        
        // Display available developers
        System.out.println("Available Developers: ");
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-12s%n", "User ID", "User Name", "User Role");
        System.out.println("-----------------------------------------------------");
        
        List<String[]> developers = UserDAO.getUsersByRole(Constants.ROLE_DEVELOPER);
        for (String[] developer : developers) {
            System.out.printf("%-10s | %-20s | %-12s%n", 
                    developer[0], developer[1], developer[3]);
        }
        
        int developerId = InputHandler.getValidInteger(scanner, "\nEnter the User ID of the developer: ");
        
        // Create the task
        TaskService.createTask(taskName, taskDescription, project.getId(), developerId);
        
        System.out.println("\nTask created and saved successfully.");
    }
    
    /**
     * Displays all tasks for a specific project
     * @param scanner Scanner for reading input
     * @param project The project to display tasks for
     */
    private static void viewTasksForProject(Scanner scanner, Project project) {
        System.out.println("\nTasks for Project Name: " + project.getName());
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-12s | %-20s%n", "Task ID", "Task Name", "Task Status", "Developer");
        System.out.println("----------------------------------------------------------");
        
        List<Task> projectTasks = TaskDAO.getTasksByProjectId(project.getId());
        
        if (projectTasks.isEmpty()) {
            System.out.println("\nNo tasks found for this project.");
        } else {
            for (Task task : projectTasks) {
                String developerName = UserDAO.getUserNameById(task.getUserId());
                System.out.printf("%-10d | %-20s | %-12s | %-20s%n",
                        task.getId(),
                        task.getName(),
                        task.getStatus(),
                        developerName);
            }
        }
        
        System.out.print("\nEnter any key to go back to the main menu: ");
        String backToMenu = scanner.nextLine();
        if (backToMenu != null) {
            pauseExecution(1000, "\nExiting back to the main menu...");
        }
    }
    
    /**
     * Displays the main menu for developers
     * @param scanner Scanner for reading input
     * @param userId The ID of the current user
     */
    public static void displayDeveloperMenu(Scanner scanner, int userId) {
        while (true) {
            List<Task> developerTasks = TaskDAO.getTasksByUserId(userId);
            
            if (developerTasks.isEmpty()) {
                System.out.println("\nNo tasks found for this developer.");
                pauseExecution(1000, "\nExiting back to the main menu...");
                return;
            }
            
            // Display tasks
            System.out.println("These are the tasks assigned to you: ");
            System.out.println("-----------------------------------------------------");
            System.out.printf("%-10s | %-20s | %-20s | %-12s%n", "Task ID", "Task Name", "Project Name", "Task Status");
            System.out.println("--------------------------------------------------------------------------");
            
            for (Task task : developerTasks) {
                String projectName = ProjectDAO.getProjectNameById(task.getProjectId());
                System.out.printf("%-10d | %-20s | %-20s | %-12s%n",
                        task.getId(),
                        task.getName(),
                        projectName,
                        task.getStatus());
            }
            
            // Get task selection
            int taskId = InputHandler.getValidInteger(scanner, "\nEnter Task ID to view details (0 to go back): ");
            
            if (taskId == 0) {
                pauseExecution(1000, "\nExiting back to the main menu...");
                return;
            }
            
            // Handle task details and updates
            handleTaskDetails(scanner, taskId, userId);
        }
    }
    
    /**
     * Handles viewing task details and updating task status
     * @param scanner Scanner for reading input
     * @param taskId The ID of the selected task
     * @param userId The ID of the current user
     */
    private static void handleTaskDetails(Scanner scanner, int taskId, int userId) {
        Task task = TaskDAO.getTaskById(taskId);
        
        if (task == null || task.getUserId() != userId) {
            System.out.println("Invalid Task ID. Please try again.");
            pauseExecution(1000, "\nExiting back to the main menu...");
            return;
        }
        
        // Display task details
        String projectName = ProjectDAO.getProjectNameById(task.getProjectId());
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-20s | %-12s | %-20s%n",
                "Task ID", "Task Name", "Project Name", "Task Status", "Task Description");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        System.out.printf("%-10d | %-20s | %-20s | %-12s | %-20s%n",
                task.getId(),
                task.getName(),
                projectName,
                task.getStatus(),
                task.getDescription());
        
        // Display options based on task status
        String taskStatus = task.getStatus();
        int taskChoice = 0;
        
        if (taskStatus.equalsIgnoreCase(Constants.STATUS_TODO)) {
            System.out.println("\n1. Mark task as In Progress\n");
            System.out.println("2. Mark task as Completed\n");
            System.out.println("3. Go back to the main menu\n");
            
            taskChoice = InputHandler.getValidInteger(scanner, "Your Choice: ");
            
            if (taskChoice == 1) {
                TaskService.updateTaskStatus(task.getId(), Constants.STATUS_IN_PROGRESS);
                System.out.println("\nTask status updated to In Progress!");
            } else if (taskChoice == 2) {
                TaskService.updateTaskStatus(task.getId(), Constants.STATUS_COMPLETED);
                System.out.println("\nTask status updated to Completed!");
            } else if (taskChoice == 3) {
                pauseExecution(1000, "\nExiting back to the main menu...");
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } else if (taskStatus.equalsIgnoreCase(Constants.STATUS_IN_PROGRESS)) {
            System.out.println("\n1. Mark task as Completed\n");
            System.out.println("2. Go back to the main menu\n");
            
            taskChoice = InputHandler.getValidInteger(scanner, "Your Choice: ");
            
            if (taskChoice == 1) {
                TaskService.updateTaskStatus(task.getId(), Constants.STATUS_COMPLETED);
                System.out.println("\nTask status updated to Completed!");
            } else if (taskChoice == 2) {
                pauseExecution(1000, "\nExiting back to the main menu...");
                return;
            } else {
                System.out.println("\nInvalid choice. Please try again.");
            }
        } else {
            System.out.println("\nThis task is already completed. No further actions can be taken.");
        }
        
        System.out.println("\n-----------------------------------------------------");
        System.out.print("\nEnter any key to go back to the main menu: ");
        String backToMenu = scanner.nextLine();
        if (backToMenu != null) {
            pauseExecution(1000, "\nExiting back to the main menu...");
        }
    }
    
    /**
     * Utility method to pause execution for a specified time
     * @param milliseconds The time to pause in milliseconds
     * @param message Message to display before pausing
     */
    private static void pauseExecution(int milliseconds, String message) {
        if (message != null && !message.isEmpty()) {
            System.out.println(message);
        }
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}