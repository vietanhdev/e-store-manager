package com.example.store.payload.user_management.response;

public class UserSummaryResponse {
    public Boolean success;
    public Long user_id;
    public String user_name;
    public String name;

    public UserSummaryResponse(Boolean success, Long user_id, String user_name, String name){
        this.success = success;
        this.user_id = user_id;
        this.user_name = user_name;
        this.name = name;
    }

}