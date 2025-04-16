package DAO;

import java.util.List;

import util.FileHandler;

// UserDAO.java
public class UserDAO {
    private static final String USER_FILE = "resources/data/users.txt";

    public static List<String> getAllUsers() {
        return FileHandler.readFromFile(USER_FILE);
    }

    public static String getUserNameById(int userId) {
        List<String> users = getAllUsers();
        for (String user : users) {
            String[] userDetails = user.split("\\|");
            if (Integer.parseInt(userDetails[0]) == userId) {
                return userDetails[1];
            }
        }
        return "Unknown";
    }

    public static int getUserIdByEmail(String email) {
        List<String> users = getAllUsers();
        for (String user : users) {
            String[] userDetails = user.split("\\|");
            if (userDetails[2].equals(email)) {
                return Integer.parseInt(userDetails[0]);
            }
        }
        return -1;
    }

    public static String getUserRoleByEmail(String email) {
        List<String> users = getAllUsers();
        for (String user : users) {
            String[] userDetails = user.split("\\|");
            if (userDetails[2].equals(email)) {
                return userDetails[3];
            }
        }
        return "";
    }
    
    public static List<String[]> getUsersByRole(String role) {
        List<String> users = getAllUsers();
        List<String[]> matchedUsers = new java.util.ArrayList<>();
    
        for (String user : users) {
            String[] userDetails = user.split("\\|");
            if (userDetails.length >= 4 && userDetails[3].trim().equalsIgnoreCase(role.trim())) {
                matchedUsers.add(userDetails);
            }
        }
    
        return matchedUsers;
    }
    
}



