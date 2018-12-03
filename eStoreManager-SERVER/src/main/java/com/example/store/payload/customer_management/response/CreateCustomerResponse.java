package com.example.store.payload.customer_management.response;

public class CreateCustomerResponse {

    private Boolean success = true;

    private Long id;

    public CreateCustomerResponse(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}