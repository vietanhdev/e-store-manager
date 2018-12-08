package com.example.store.payload.invoice_management.request;

public class UpdateInvoiceRequest {
    
    private Float amount;

    private String purpose;

    private String description;

    public Float getAmount(){
        return amount;
    }

    public String getPurpose(){
        return purpose;
    }

    public String getDescription(){
        return description;
    }

}