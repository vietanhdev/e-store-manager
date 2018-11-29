package com.example.store.payload.customer_management.response;

public class CreateCustomerResponse {
    public Boolean success;
    public Long id;

    public CreateCustomerResponse(Boolean success, Long id){
        this.success = success;
        this.id = id;
    }
}