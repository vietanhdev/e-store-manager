package com.example.store.payload.user_management.response;

public class UserSummaryResponse {
    public Boolean success;
    public Long id;
    public String username;
    public String name;

    public UserSummaryResponse(Boolean success, Long id, String username, String name){
        this.success = success;
        this.id = id;
        this.username = username;
        this.name = name;
    }

}