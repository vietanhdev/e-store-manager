package com.example.store.payload.user_management.response;

public class CreateUserResponse {
    private Boolean success = true;
    private Long id;

    public CreateUserResponse(Long id){
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