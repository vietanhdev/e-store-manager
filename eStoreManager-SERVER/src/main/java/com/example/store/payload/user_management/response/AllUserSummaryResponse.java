package com.example.store.payload.user_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllUserSummaryResponse {
    public Boolean success;
    public Set<UserSummary> users = new HashSet<>();

    public AllUserSummaryResponse(Boolean success){
        this.success = success;
    }

    public void addUser(Long id, String username, String name){
        this.users.add(new UserSummary(id, username, name));
    }

}