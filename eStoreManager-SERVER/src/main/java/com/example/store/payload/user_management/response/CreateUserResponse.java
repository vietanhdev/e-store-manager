package com.example.store.payload.user_management.response;

public class CreateUserResponse {
    public Boolean success;
    public String user_id;

    public CreateUserResponse(Boolean success, String user_id){
        this.success = success;
        this.user_id = user_id;
    }

}