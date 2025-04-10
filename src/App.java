import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Project;
import model.Task;
import util.InputHandler;
import util.FileHandler;

public class App {
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome to the Project Management System!\n");

        // infinite loop to keep the program running until the user chooses to exit
        while (true) {

            // Read user data from file
            List<String> usersList = FileHandler.readFromFile("resources/data/users.txt");

            // Get user email for login
            String email = InputHandler.getValidString(scanner, "Enter your email: ");
            String userRole = "";

            // we used an array to store the userId because we need to modify it inside the
            // loop
            // and we cannot modify the value of a primitive type directly inside the loop.
            final int[] currentUserId = { -1 };

            // Loop through the list of users to find the user with the entered email
            for (String user : usersList) {
                String[] userDetails = user.split("\\|"); // Split user details by delimiter '|'
                if (userDetails[2].equals(email)) { // Check if the email matches
                    userRole = userDetails[3]; // Assign the user's role
                    currentUserId[0] = Integer.parseInt(userDetails[0]); // Assign the user's ID
                    break; // Exit the loop once the user is found
                }
            }

            if (userRole.isEmpty()) { // If no user is found with the entered email
                System.out.println("\nUser not found. Please check your email.\n");
                continue; // Ask for email again
            } else if (userRole.equals("PROJECT_MANAGER")) { // If the user is a project manager

                // project manager
                System.out.println("\nWelcome Project Manager : " + email + "!");

                // Infinite loop for project manager menu
                while (true) {

                    @SuppressWarnings("unchecked")
                    List<Project> projectsList = (List<Project>) FileHandler
                            .readObjectFromFile("resources/data/projects.dat"); // Read projects from file

                    if (projectsList == null)
                        projectsList = new ArrayList<>(); // Initialize projects list if null

                    System.out.println("\n1. Create a new project");
                    System.out.println("\n2. View all projects");
                    System.out.println("\n3. Exit\n");

                    int choice = InputHandler.getValidInteger(scanner, "Enter your choice: "); // Get user choice

                    if (choice == 1) {

                        // Input project data
                        String projectName = InputHandler.getValidString(scanner, "\nProject Name: ");
                        String projectDeadline = InputHandler.getValidString(scanner,
                                "\nProject Deadline (DD-MM-YYYY): ");// Get project deadline
                        int projectId = projectsList.size() + 1; // Generate new project ID based on size

                        // Create and add new project
                        Project newProject = new Project(projectId, projectName, projectDeadline, "Unfinished",
                                currentUserId[0]);
                        projectsList.add(newProject); // Add new project to the list

                        // Write the entire updated list back to the file (overwrites)
                        FileHandler.writeObjectToFile("resources/data/projects.dat", projectsList);

                        System.out.println("\nProject created successfully!");
                        System.out.println("\nExiting back to the main menu...\n");
                        // Pause for 3 seconds before going back to the main menu
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (choice == 2) {
                        // fliter for current projec manager
                        if (projectsList == null)
                            projectsList = new ArrayList<>();

                        // Filter projects for the current project manager
                        List<Project> managerProjects = new ArrayList<>();
                        for (Project project : projectsList) {
                            if (project.getUserId() == currentUserId[0]) {
                                managerProjects.add(project);
                            }
                        }

                        if (managerProjects.isEmpty()) { // Check if the projects list is empty
                            System.out.println("\nNo projects found.\n"); // If empty, inform the user
                            System.out.println("\nExiting back to the main menu...\n");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;// Go back to the main menu
                        } else {
                            System.out.println("\nProject ID | Project Name   | Project Deadline  | Project Status");
                            System.out.println("---------------------------------------------------------------");
                            for (Project project : managerProjects) {
                                System.out.printf("%-10d | %-14s | %-17s | %s%n",
                                        project.getId(),
                                        project.getName(),
                                        project.getDeadline(),
                                        project.getStatus());
                            } // Display project manager's own projects
                        }
                        int projectId = InputHandler.getValidInteger(scanner,
                                "\nEnter Project ID to view details (0 to go back): "); // Get project ID from user

                        if (projectId == 0) {// If user enters 0, go back to the main menu
                            // Pause for 3 seconds before going back to the main menu
                            System.out.println("\nExiting back to the main menu...\n");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        } else {
                            if (projectId > 0 && projectId <= projectsList.size()) { // Check if the project ID is valid
                                Project project = projectsList.get(projectId - 1); // Get the actual Project object
                                // Check if the project belongs to the current project manager
                                if (project.getUserId() != currentUserId[0]) {
                                    System.out.println("\nThis project doesn't belong to you. Access denied.");
                                    System.out.println("\nExiting back to the main menu...\n");
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    continue;
                                }

                                boolean isFinished = project.getStatus().equalsIgnoreCase("Finished"); // Check if the
                                                                                                       // project is
                                                                                                       // finished

                                System.out.println("\nProject ID: " + project.getId());
                                System.out.println("\nProject Name: " + project.getName());
                                System.out.println("\nProject Deadline: " + project.getDeadline());
                                System.out.println("\nProject Status: " + project.getStatus());
                                System.out.println("\n-----------------------------------------------------");
                                if (isFinished) { // If the project is finished, inform the user
                                    System.out.println(
                                            "This project is already finished. No further actions can be taken.");
                                    // Pause for 3 seconds before going back to the main menu
                                    System.out.println("\nExiting back to the main menu...\n");
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    continue;
                                }

                                // Display options for the project if not finished
                                System.out.println("\n1. Mark project as finished\n");
                                System.out.println("2. Create a task for this project\n");
                                System.out.println("3. View tasks for this project\n");
                                System.out.println("4. Go back to the main menu\n");

                                int projectChoice = InputHandler.getValidInteger(scanner, "Your Choice: ");
                                Project chosenProject = projectsList.get(projectId - 1);

                                if (projectChoice == 1) {

                                    // Update status in object
                                    chosenProject.setStatus("Finished");

                                    // Save updated list back to file
                                    FileHandler.writeObjectToFile("resources/data/projects.dat", projectsList);

                                    System.out.println("\nProject status updated to Finished!");
                                    System.out.println("\nExiting back to the main menu...");
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                } else if (projectChoice == 2) {
                                    System.out
                                            .println("\nCreating a task for Project Name: " + chosenProject.getName());

                                    @SuppressWarnings("unchecked")
                                    // Load existing tasks
                                    List<Task> tasksList = (List<Task>) FileHandler
                                            .readObjectFromFile("resources/data/tasks.dat");
                                    if (tasksList == null)
                                        tasksList = new ArrayList<>();

                                    // Create new task
                                    String taskName = InputHandler.getValidString(scanner, "Task Name: ");
                                    String taskDescription = InputHandler.getValidString(scanner, "Task Description: ");
                                    String taskStatus = "To Do";
                                    int taskId = tasksList.size() + 1;
                                    int taskProjectId = chosenProject.getId(); // Use the selected project's ID
                                    System.out.println("Availabe Developers: ");
                                    System.out.println("-----------------------------------------------------");
                                    System.out.printf("%-10s | %-20s | %-12s%n", "User ID", "User Name", "User Role");
                                    System.out.println("-----------------------------------------------------");
                                    List<String> usersList1 = FileHandler.readFromFile("resources/data/users.txt");
                                    for (String user : usersList1) {
                                        String[] userDetails = user.split("\\|");
                                        if (userDetails[3].equals("DEVELOPER")) {
                                            System.out.printf("%-10s | %-20s | %-12s%n",
                                                    userDetails[0],
                                                    userDetails[1],
                                                    userDetails[3]);
                                        }
                                    }
                                    int developerId = InputHandler.getValidInteger(scanner,
                                            "\nEnter the User ID of the developer: ");

                                    Task newTask = new Task(taskId, taskName, taskDescription, taskStatus,
                                            taskProjectId, developerId);
                                    tasksList.add(newTask);

                                    // Save updated task list
                                    FileHandler.writeObjectToFile("resources/data/tasks.dat", tasksList);

                                    System.out.println("\nTask created and saved successfully.");
                                } else if (projectChoice == 3) {
                                    System.out.println("\nTasks for Project Name: " + chosenProject.getName());
                                    System.out.println("----------------------------------------------------------");
                                    System.out.printf("%-10s | %-20s | %-12s | %-20s%n", "Task ID", "Task Name",
                                            "Task Status", "Developer");
                                    System.out.println("----------------------------------------------------------");
                                    @SuppressWarnings("unchecked")
                                    // Read all tasks from file
                                    List<Task> allTasks = (List<Task>) FileHandler
                                            .readObjectFromFile("resources/data/tasks.dat");
                                    if (allTasks == null)
                                        allTasks = new ArrayList<>();

                                    // Filter and display tasks for the selected project
                                    boolean hasTasks = false;
                                    for (Task task : allTasks) {
                                        if (task.getProjectId() == chosenProject.getId()) {
                                            // Find developer name using the userId from the task
                                            String developerName = "Unknown";
                                            for (String user : usersList) {
                                                String[] userDetails = user.split("\\|");
                                                if (Integer.parseInt(userDetails[0]) == task.getUserId()) {
                                                    developerName = userDetails[1]; // Get developer name
                                                    break;
                                                }
                                            }
                                            System.out.printf("%-10d | %-20s | %-12s | %-20s%n",
                                                    task.getId(),
                                                    task.getName(),
                                                    task.getStatus(),
                                                    developerName);
                                            hasTasks = true;
                                        }
                                    }

                                    if (!hasTasks) {
                                        System.out.println("\nNo tasks found for this project.");
                                    }

                                    System.out.print("\nEnter any key to go back to the main menu: ");
                                    String backToMenu = scanner.nextLine();
                                    if (backToMenu != null && !backToMenu.isEmpty()) {
                                        System.out.println("\nExiting back to the main menu...");
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    continue;

                                } else if (projectChoice == 4) {
                                    System.out.println("\nExiting back to the main menu...");
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    System.out.println("Invalid choice. Please try again.");
                                }

                            } else {
                                System.out.println("Invalid Project ID. Please try again.");
                            }

                        }

                    } else if (choice == 3) {
                        System.out.println("\nExiting the program. Goodbye!");
                        System.exit(0);

                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }

                }
            } else if (userRole.equals("DEVELOPER")) {
                // developer
                System.out.println("\nWelcome Developer : " + email + "!");

                while (true) {
                    System.out.println("These are the tasks assigned to you: ");
                    System.out.println("-----------------------------------------------------");
                    System.out.printf("%-10s | %-20s | %-20s | %-12s%n", "Task ID", "Task Name", "Project Name",
                            "Task Status");
                    System.out.println("--------------------------------------------------------------------------");
                    @SuppressWarnings("unchecked")
                    // Read all tasks from file
                    List<Task> allTasks = (List<Task>) FileHandler
                            .readObjectFromFile("resources/data/tasks.dat");
                    if (allTasks == null)
                        allTasks = new ArrayList<>();

                    @SuppressWarnings("unchecked")
                    // Read all projects from file
                    List<Project> allProjects = (List<Project>) FileHandler
                            .readObjectFromFile("resources/data/projects.dat");
                    if (allProjects == null)
                        allProjects = new ArrayList<>();

                    boolean hasTasks = false;
                    for (Task task : allTasks) {
                        if (task.getUserId() == currentUserId[0]) {
                            String projectName = allProjects.stream()
                                    .filter(project -> project.getId() == task.getProjectId())
                                    .map(Project::getName)
                                    .findFirst()
                                    .orElse("Unknown Project");
                            System.out.printf("%-10d | %-20s | %-20s | %-12s%n",
                                    task.getId(),
                                    task.getName(),
                                    projectName,
                                    task.getStatus());
                            hasTasks = true;
                        }
                    }
                    if (!hasTasks) {
                        System.out.println("\nNo tasks found for this developer.");
                        System.out.println("\nExiting back to the main menu...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    int taskId = InputHandler.getValidInteger(scanner,
                            "\nEnter Task ID to view details (0 to go back): ");
                    if (taskId == 0) {
                        System.out.println("\nExiting back to the main menu...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    Task task = allTasks.stream()
                            .filter(t -> t.getId() == taskId && t.getUserId() == currentUserId[0])
                            .findFirst()
                            .orElse(null);
                    if (task == null) {
                        System.out.println("Invalid Task ID. Please try again.");
                        System.out.println("\nExiting back to the main menu...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    System.out.println("-----------------------------------------------------");
                    System.out.printf("%-10s | %-20s | %-20s | %-12s | %-20s%n",
                            "Task ID", "Task Name", "Project Name", "Task Status", "Task Description");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");

                    System.out.printf("%-10d | %-20s | %-20s | %-12s | %-20s%n",
                            task.getId(),
                            task.getName(),
                            allProjects.stream()
                                    .filter(project -> project.getId() == task.getProjectId())
                                    .map(Project::getName)
                                    .findFirst()
                                    .orElse("Unknown Project"),
                            task.getStatus(),
                            task.getDescription());

                    String taskStatus = task.getStatus();
                    if (taskStatus.equalsIgnoreCase("To Do")) {
                        System.out.println("\n1. Mark task as In Progress\n");
                        System.out.println("2. Mark task as Completed\n");
                        System.out.println("3. Go back to the main menu\n");

                        int taskChoice = InputHandler.getValidInteger(scanner, "Your Choice: ");

                        if (taskChoice == 1) {
                            task.setStatus("In Progress");
                            System.out.println("\nTask status updated to In Progress!");
                        } else if (taskChoice == 2) {
                            task.setStatus("Completed");
                            System.out.println("\nTask status updated to Completed!");
                        } else if (taskChoice == 3) {
                            System.out.println("\nExiting back to the main menu...");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    } else if (taskStatus.equalsIgnoreCase("In Progress")) {
                        System.out.println("\n1. Mark task as Completed\n");
                        System.out.println("2. Go back to the main menu\n");

                        int taskChoice = InputHandler.getValidInteger(scanner, "Your Choice: ");

                        if (taskChoice == 1) {
                            task.setStatus("Completed");
                            System.out.println("\nTask status updated to Completed!");
                        } else if (taskChoice == 2) {
                            System.out.println("\nExiting back to the main menu...");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        } else {
                            System.out.println("\nInvalid choice. Please try again.");
                        }
                    } else {
                        System.out.println("\nThis task is already completed. No further actions can be taken.");
                    }
                    System.out.println("\n-----------------------------------------------------");
                    // Save updated task list
                    @SuppressWarnings("unchecked")
                    List<Task> tasksList = (List<Task>) FileHandler
                            .readObjectFromFile("resources/data/tasks.dat");
                    if (tasksList == null)
                        tasksList = new ArrayList<>();
                    tasksList.removeIf(t -> t.getId() == task.getId() && t.getUserId() == currentUserId[0]);
                    tasksList.add(task);
                    FileHandler.writeObjectToFile("resources/data/tasks.dat", tasksList);
                    System.out.print("\nEnter any key to go back to the main menu: ");
                    String backToMenu = scanner.nextLine();
                    if (backToMenu != null && !backToMenu.isEmpty()) {
                        System.out.println("\nExiting back to the main menu...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    continue;
                }
            } else {
                System.out.println("\nWelcome Developer : " + email + "!");
            }
        }
    }
}
