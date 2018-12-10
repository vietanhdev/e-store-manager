package com.example.store.payload.user_management.response;

import java.util.ArrayList;
import java.util.List;

public class AllUserInforResponse {
    private Boolean success = true;
    private List<UserInfor> users = new ArrayList<>();

    public AllUserInforResponse(){
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<UserInfor> getUsers(){
        return users;
    }

    public void setUsers(List<UserInfor> users){
        this.users = users;
    }

    public void addUserInfor(UserInfor userInfor) {
        this.users.add(userInfor);
    }

}