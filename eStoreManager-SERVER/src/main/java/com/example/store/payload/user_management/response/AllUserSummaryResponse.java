package com.example.store.payload.user_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllUserSummaryResponse {
    public Boolean success;
    public Set<UserSummary> users = new HashSet<>();

    public AllUserSummaryResponse(Boolean success){
        this.success = success;
    }

    public void addUsers(Long user_id, String user_name, String name){
        this.users.add(new UserSummary(user_id, user_name, name));
    }

}