package com.example.store.payload.invoice_management.response;

public class InvoiceInforResponse {

    private Boolean success = true;

    private Long id;

    private Float amount;

    private String purpose;

    private String description;

    public InvoiceInforResponse(Long id, Float amount, String purpose, String description) {
        this.id = id;
        this.amount = amount;
        this.purpose = purpose;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}