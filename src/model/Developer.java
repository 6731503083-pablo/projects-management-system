package model;
import java.util.ArrayList;
import model.enums.UserRoles;

public class Developer extends User{
    private ArrayList<Task> assignedTasks;
    
    public Developer(int id, String name, String email, UserRoles role) {
        super(id, name, email, role);
    }

    public ArrayList<Task> getAssignedTasks() {
        return assignedTasks;
    }
}