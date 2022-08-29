package com.revature.lmp1.models;

public class Reimb_Status {

    private String status_id;
    private String status;

    public Reimb_Status(){

    }
    public Reimb_Status(String status_id, String status){
        this.status_id = status_id;
        this.status = status;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
