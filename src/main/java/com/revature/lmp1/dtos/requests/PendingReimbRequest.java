package com.revature.lmp1.dtos.requests;

public class PendingReimbRequest {
    private String id;

    public PendingReimbRequest() {
    }

    public PendingReimbRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserIdRequest{" +
                "id='" + id + '\'' +
                '}';
    }
}
