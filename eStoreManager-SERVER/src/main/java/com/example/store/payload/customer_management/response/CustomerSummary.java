package com.example.store.payload.customer_management.response;

public class CustomerSummary {
    public Long id;
    public String name;

    public CustomerSummary(Long id, String name){
        this.id = id;
        this.name = name;
    }

}