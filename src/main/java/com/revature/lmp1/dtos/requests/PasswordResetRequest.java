package com.revature.lmp1.dtos.requests;

public class PasswordResetRequest {
    private String id;
    private String password1;
    private String password2;

    public PasswordResetRequest() {
    }

    public PasswordResetRequest(String id, String password1, String password2) {
        this.id = id;
        this.password1 = password1;
        this.password2 = password2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public String toString() {
        return "UserIdRequest{" +
                "id='" + id + '\'' +
                '}';
    }
}
