package com.example.store.payload.user_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllUserInforResponse {
    public Boolean success = true;
    public Set<UserInfor> users = new HashSet<>();

    public AllUserInforResponse(){
    }

    public void addUserInfor(UserInfor userInfor){
        this.users.add(userInfor);
    }

}