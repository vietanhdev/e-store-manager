package com.example.store.payload.user_management.response;

public class UserSummary {
    public Long user_id;
    public String user_name;
    public String name;

    public UserSummary(Long user_id, String user_name, String name){
        this.user_id = user_id;
        this.user_name = user_name;
        this.name = name;
    }

}