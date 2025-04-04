import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LoginSystem {
    private static final String USERS_FILE = "users.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Login System ---");
        System.out.print("Enter your email: ");
        String email = sc.nextLine().trim();

        if(isEmailRegistered(email)) {
            System.out.println("Login success! Welcome back.");
        } else {
            System.out.println("This email is not registered! Please sign up first;");
        }
        sc.close();
    }

    private static boolean isEmailRegistered(String email) {
        File file = new File(USERS_FILE);

        if(!file.exists()) {
            System.err.println("Error: Users database not found!");
            return false;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // Read file line by line
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length > 0 && parts[0].equalsIgnoreCase(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users file: " + e.getMessage());
        }
        return false;
    }
}
