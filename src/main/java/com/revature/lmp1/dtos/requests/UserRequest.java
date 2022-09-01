package com.revature.lmp1.dtos.requests;

public class UserRequest {
    private String id;
    private String role;

    public UserRequest() {
    }

    public UserRequest(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserIdRequest{" +
                "id='" + id + '\'' +
                "role='" + role + '\'' +
                '}';
    }
}
