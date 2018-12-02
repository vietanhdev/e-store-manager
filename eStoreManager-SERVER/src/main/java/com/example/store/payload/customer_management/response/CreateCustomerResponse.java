package com.example.store.payload.customer_management.response;

public class CreateCustomerResponse {
    public Boolean success = true;
    public Long id;

    public CreateCustomerResponse(Long id){
        this.id = id;
    }
}