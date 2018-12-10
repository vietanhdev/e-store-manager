package com.example.store.payload.product_management.response;

public class CreateProductResponse {

    private Boolean success = true;

    private Long id;

    public CreateProductResponse(Long id){
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