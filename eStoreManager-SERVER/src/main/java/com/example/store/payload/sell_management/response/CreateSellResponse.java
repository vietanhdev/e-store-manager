package com.example.store.payload.sell_management.response;

public class CreateSellResponse {

    private Boolean success = true;

    private Long id;

    public CreateSellResponse(Long id){
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