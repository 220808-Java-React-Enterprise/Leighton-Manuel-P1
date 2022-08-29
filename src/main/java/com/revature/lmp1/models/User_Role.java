package com.revature.lmp1.models;

public class User_Role {

    private String role_id;
    private String role;

    public User_Role(){

    }
    public User_Role(String role_id, String role){
        this.role_id = role_id;
        this.role = role;
    }

    public String getRole_id() {
        return role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
