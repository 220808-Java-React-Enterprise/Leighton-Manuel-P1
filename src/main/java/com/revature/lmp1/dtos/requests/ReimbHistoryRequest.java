package com.revature.lmp1.dtos.requests;

public class ReimbHistoryRequest {
    private String reimbStatus;
    private String date;
    private String order;

    public ReimbHistoryRequest() {
    }

    public ReimbHistoryRequest(String reimbStatus, String date, String order) {

        this.reimbStatus = reimbStatus;
        this.date = date;
        this.order = order;
    }



    public String getReimbStatus() {
        return reimbStatus;
    }

    public void setReimbStatus(String reimbStatus) {
        this.reimbStatus = reimbStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "ReimbHistoryRequest{" +

                ", status='" + reimbStatus + '\'' +
                ", date='" + date + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
