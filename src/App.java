
import java.util.List;
import java.util.Scanner;
import model.Project;
import util.InputHandler;
import util.FileHandler;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome to the Projects Management System!\n");
        String email = InputHandler.getValidString(scanner, "Enter your email: ");

        // project manager
        System.out.println("\nWelcome Project Manager : " + email + "!");

        while (true) {
            List<String> projectsList = FileHandler.readFromFile("resources/data/projects.txt");

            System.out.println("\n1. Create a new project");
            System.out.println("2. View all projects");
            System.out.println("3. Exit\n");

            int choice = InputHandler.getValidInteger(scanner, "Enter your choice: ");

            if (choice == 1) {
                String projectName = InputHandler.getValidString(scanner, "\nProject Name: ");
                String projectDeadline = InputHandler.getValidString(scanner, "\nProject Deadline (DD-MM-YYYY): ");
                int projectId = projectsList.size() + 1;
                Project newProject = new Project(projectId, projectName, projectDeadline, "Unfinished");
                FileHandler.writeAndAppendFile("resources/data/projects.txt", newProject.toString());
                System.out.println("Project created successfully!");
                System.out.println("Exiting back to the main menu...\n");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (choice == 2) {

                if (projectsList.isEmpty()) {
                    System.out.println("No projects found.\n");
                    continue;
                } else {
                    System.out.println("\nProject ID | Project Name | Project Deadline | Project Status");
                    System.out.println("-----------------------------------------------------");
                    for (String project : projectsList) {
                        String[] projectDetails = project.split("\\|");
                        System.out.printf("%-10s | %-13s | %-16s | %s%n", projectDetails[0], projectDetails[1],
                                projectDetails[2], projectDetails[3]);
                    }
                }
                int projectId = InputHandler.getValidInteger(scanner,
                        "\nEnter Project ID to view details (0 to go back): ");
                if (projectId == 0) {
                    System.out.println("\nExiting back to the main menu...\n");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                } else {
                    if (projectId > 0 && projectId <= projectsList.size()) {
                        String projectDetails = projectsList.get(projectId - 1);
                        System.out.println("\nProject ID: " + projectDetails.split("\\|")[0]);
                        System.out.println("Project Name: " + projectDetails.split("\\|")[1]);
                        System.out.println("Project Deadline: " + projectDetails.split("\\|")[2]);
                        System.out.println("Project Status: " + projectDetails.split("\\|")[3]);
                        System.out.println("-----------------------------------------------------");
                        System.out.println("\n1. Update Project Status to Finished");
                        System.out.println("2. Create a task for this project");
                        System.out.println("3. Go back to the main menu\n");
                        int projectChoice = InputHandler.getValidInteger(scanner, "Your Choice: ");
                        if (projectChoice == 1) {
                            Project updatedProject = new Project(projectId, projectDetails.split("\\|")[1],
                                    projectDetails.split("\\|")[2], "Finished");
                            FileHandler.updateFile("resources/data/projects.txt", projectDetails,
                                    updatedProject.toString());
                            System.out.println("\nProject status updated to Finished!");
                            System.out.println("\nExiting back to the main menu...");
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } else if (projectChoice == 2) {
                            System.out.println("Creating a task for Project ID: " + projectId);
                            // Implement task creation logic here
                        } else if (projectChoice == 3) {
                            System.out.println("\nExiting back to the main menu...");
                            try {
                                Thread.sleep(3000);
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
                System.out.println("Exiting the program. Goodbye!");
                System.exit(0);

            } else {
                System.out.println("Invalid choice. Please try again.");
            }

        }
    }
}
