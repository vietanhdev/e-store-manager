package com.example.store.payload.customer_management.response;

public class CreateCustomerResponse {
    public Boolean success;
    public Long user_id;

    public CreateCustomerResponse(Boolean success, Long user_id){
        this.success = success;
        this.user_id = user_id;
    }
}