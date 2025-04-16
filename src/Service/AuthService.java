package Service;

import DAO.UserDAO;

public class AuthService {
    public static int authenticateUser(String email) {
        String userRole = UserDAO.getUserRoleByEmail(email);
        int userId = UserDAO.getUserIdByEmail(email);
        
        if (userRole.isEmpty()) {
            return -1; // Authentication failed
        }
        
        return userId;
    }
    
    public static String getUserRole(String email) {
        return UserDAO.getUserRoleByEmail(email);
    }
}