package com.example.store.payload.user_management.response;

public class CreateUserResponse {
    public Boolean success;
    public Long id;

    public CreateUserResponse(Boolean success, Long id){
        this.success = success;
        this.id = id;
    }

}