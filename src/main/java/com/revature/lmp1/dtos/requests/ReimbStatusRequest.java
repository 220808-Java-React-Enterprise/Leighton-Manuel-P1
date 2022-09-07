package com.revature.lmp1.dtos.requests;

public class ReimbStatusRequest {
    private String id;
    private String currentStatus;

    public ReimbStatusRequest() {
    }

    public ReimbStatusRequest(String id, String currentStatus) {
        this.id = id;
        this.currentStatus = currentStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentStatus(){
        return currentStatus;
    }
    public void setStatus(String role){
        this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
        return "StatusRequest{" +
                "id='" + id + '\'' +
                "Status='" + currentStatus + '\'' +
                '}';
    }
}
