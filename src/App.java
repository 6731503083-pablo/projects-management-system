import java.util.Scanner;

import Service.AuthService;
import Service.MenuService;
import util.Constants;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome to the Project Management System!\n");

        // infinite loop to keep the program running until the user chooses to exit
        while (true) {
            // Get user email for login
            String email = MenuService.promptForUserEmail(scanner);
            
            // Authenticate user
            int userId = AuthService.authenticateUser(email);
            String roleOfUser = AuthService.getUserRole(email);
            
            if (userId == -1) {
                System.out.println("\nUser not found. Please check your email.\n");
                continue;
            }
            
            // Display appropriate welcome message
            System.out.println("\nWelcome " + (roleOfUser.equals(Constants.ROLE_PROJECT_MANAGER) ? 
                "Project Manager" : "Developer") + " : " + email + "!");
            
            // Route to appropriate menu based on user role
            if (roleOfUser.equals(Constants.ROLE_PROJECT_MANAGER)) {
                MenuService.displayProjectManagerMenu(scanner, userId);
            } else if (roleOfUser.equals(Constants.ROLE_DEVELOPER)) {
                MenuService.displayDeveloperMenu(scanner, userId);
            } else {
                System.out.println("\nUnknown role. Please contact administrator.");
            }
        }
    }
}