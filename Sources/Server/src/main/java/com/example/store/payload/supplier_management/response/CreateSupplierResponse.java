package com.example.store.payload.supplier_management.response;

public class CreateSupplierResponse {

    private Boolean success = true;
    
    private Long id;

    public CreateSupplierResponse(Long id){
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