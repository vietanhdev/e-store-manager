package com.example.store.payload.user_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllUserInforResponse {
    private Boolean success = true;
    private Set<UserInfor> users = new HashSet<>();

    public AllUserInforResponse(){
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void addUserInfor(UserInfor userInfor) {
        this.users.add(userInfor);
    }

}