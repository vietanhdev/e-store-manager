package com.example.store.payload.supplier_management.response;

public class CreateSupplierResponse {
    public Boolean success = true;
    public Long id;

    public CreateSupplierResponse(Long id){
        this.id = id;
    }
}