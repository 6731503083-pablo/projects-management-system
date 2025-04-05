package util;

import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner;

    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    public static int getValidInteger(Scanner scanner, String prompt) {
        int num;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                num = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                return num;
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    public static String getValidString(Scanner scanner, String prompt) {
        String str;
        while (true) {
            System.out.print(prompt);
            str = scanner.nextLine();
            if (!str.trim().isEmpty()) {
                return str;
            } else {
                System.out.println("Invalid email. Please enter a valid email.");
            }
        }
    }

    public void close() {
        scanner.close();
    }
}