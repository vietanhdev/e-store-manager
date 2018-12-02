package com.example.store.payload.product_type_management.response;

public class CreateProductTypeResponse {
    public Boolean success = true;
    public Long id;

    public CreateProductTypeResponse(Long id){
        this.id = id;
    }
}