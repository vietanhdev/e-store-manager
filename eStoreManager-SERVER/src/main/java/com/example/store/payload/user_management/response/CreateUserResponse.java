package com.example.store.payload.user_management.response;

public class CreateUserResponse {
    public Boolean success = true;
    public Long id;

    public CreateUserResponse(Long id){
        this.id = id;
    }

}