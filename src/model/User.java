package model;

import model.enums.UserRoles;

public class User {
    private int id;
    private String name;
    private String email;
    private UserRoles role;

    public User(){
        //default constructor
    }

    //full constructor 
    public User(int id, String name, String email, UserRoles role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Constructor without ID (e.g., auto-generated later)
    public User(String name, String email, UserRoles role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }
}