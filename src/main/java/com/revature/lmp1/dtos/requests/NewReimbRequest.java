package com.revature.lmp1.dtos.requests;

public class NewReimbRequest {

    private double amount;
    private String description;
    private String type;


    public NewReimbRequest() {
    }

    public NewReimbRequest(double amount, String description, String type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NewReimbRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
