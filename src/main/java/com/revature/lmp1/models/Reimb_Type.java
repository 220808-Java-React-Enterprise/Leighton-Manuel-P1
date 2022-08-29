package com.revature.lmp1.models;

public class Reimb_Type {
    private String type_id;
    private String type;

    public Reimb_Type(){

    }

    public Reimb_Type(String type_id, String type){
        this.type_id = type_id;
        this.type = type;
    }

    public String getType_id() {
        return type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
