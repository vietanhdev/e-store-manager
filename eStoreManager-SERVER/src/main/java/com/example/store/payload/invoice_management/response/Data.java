package com.example.store.payload.invoice_management.response;

public class Data {

    public Long id;
    
    public Float amount;

    public String purpose;

    public String description;

    public Data(Long id, Float amount, String purpose, String description) {
        this.id = id;
        this.amount = amount;
        this.purpose = purpose;
        this.description = description;
    }

}