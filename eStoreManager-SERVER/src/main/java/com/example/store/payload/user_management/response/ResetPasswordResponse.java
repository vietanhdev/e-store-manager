package com.example.store.payload.user_management.response;

public class ResetPasswordResponse {
    public Boolean success;
    public String password;

    public ResetPasswordResponse(Boolean success, String password){
        this.success = success;
        this.password = password;
    }

}