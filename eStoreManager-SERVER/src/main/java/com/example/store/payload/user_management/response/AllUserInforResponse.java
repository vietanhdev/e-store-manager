package com.example.store.payload.user_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllUserInforResponse {
    public Boolean success;
    public Set<UserInfor> users = new HashSet<>();

    public AllUserInforResponse(Boolean success){
        this.success = success;
    }

    public void addUserInfor(UserInfor userInfor){
        this.users.add(userInfor);
    }

}