package com.revature.lmp1.models;

import java.sql.Blob;
import java.time.LocalDateTime;

public class Reimbursement {
    private String id;
    private int amount;
    private LocalDateTime submitted;
    private LocalDateTime resolved;
    private String description;
    private Blob receipt;
    private String payment_id;
    private String author_id;
    private String resolver_id;
    private String status_id;
    private String type_id;

    public Reimbursement() {
    }

    public Reimbursement(String id, int amount, LocalDateTime submitted, LocalDateTime resolved,String description, Blob receipt, String payment_id, String author_id, String resolver_id, String status_id, String type_id){
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.payment_id = payment_id;
        this.author_id = author_id;
        this.resolver_id = resolver_id;
        this.status_id = status_id;
        this.type_id = type_id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public LocalDateTime getResolved() {
        return resolved;
    }

    public void setResolved(LocalDateTime resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Blob getReceipt() {
        return receipt;
    }

    public void setReceipt(Blob receipt) {
        this.receipt = receipt;
    }

    public String getPaymentId() {
        return payment_id;
    }

    public void setPaymentId(String paymentId) {
        this.payment_id = paymentId;
    }

    public String getAuthorId() {
        return author_id;
    }

    public void setAuthorId(String authorId) {
        this.author_id = authorId;
    }

    public String getResolverId() {
        return resolver_id;
    }

    public void setResolverId(String resolverId) {
        this.resolver_id = resolverId;
    }

    public String getStatusId() {
        return status_id;
    }

    public void setStatusId(String statusId) {
        this.status_id = statusId;
    }

    public String getTypeId() {
        return type_id;
    }

    public void setTypeId(String typeId) {
        this.type_id = typeId;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", paymentId='" + payment_id + '\'' +
                ", authorId='" + author_id + '\'' +
                ", resolverId='" + resolver_id + '\'' +
                ", statusId='" + status_id + '\'' +
                ", typeId='" + type_id + '\'' +
                '}';
    }
}
