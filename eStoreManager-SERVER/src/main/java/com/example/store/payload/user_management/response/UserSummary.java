package com.example.store.payload.user_management.response;

public class UserSummary {
    public Long id;
    public String username;
    public String name;

    public UserSummary(Long id, String username, String name){
        this.id = id;
        this.username = username;
        this.name = name;
    }

}