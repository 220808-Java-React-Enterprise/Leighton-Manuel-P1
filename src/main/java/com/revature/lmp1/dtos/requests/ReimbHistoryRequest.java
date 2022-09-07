package com.revature.lmp1.dtos.requests;

public class ReimbHistoryRequest {
    private String id;
    private String reimbStatus;
    private String date;
    private String order;

    public ReimbHistoryRequest() {
    }

    public ReimbHistoryRequest(String id, String reimbStatus, String date, String order) {
        this.id = id;
        this.reimbStatus = reimbStatus;
        this.date = date;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return reimbStatus;
    }

    public void setStatus(String status) {
        this.reimbStatus = status;
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
                "id='" + id + '\'' +
                ", status='" + reimbStatus + '\'' +
                ", date='" + date + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
