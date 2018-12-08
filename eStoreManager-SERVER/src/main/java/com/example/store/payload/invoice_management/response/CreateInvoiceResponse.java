package com.example.store.payload.invoice_management.response;

public class CreateInvoiceResponse {

    private Boolean success = true;
    
    private Long id;

    public CreateInvoiceResponse(Long id){
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