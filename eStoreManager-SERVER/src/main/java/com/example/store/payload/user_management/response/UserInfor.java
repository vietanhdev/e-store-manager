package com.example.store.payload.user_management.response;

import java.util.ArrayList;
import java.util.List;

public class UserInfor {
    public Long id;
    public String name;
    public String username;
    public Long salary;
    public String email;
    public String address;
    public String mobileNo;
    public List<String> roles = new ArrayList<>();

    public UserInfor(Long id, String name, String username, Long salary, String email, String address, String mobileNo){
        this.id = id;
        this.name = name;
        this.username = username;
        this.salary = salary;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }
}