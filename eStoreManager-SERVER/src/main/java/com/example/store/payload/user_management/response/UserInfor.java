package com.example.store.payload.user_management.response;

import java.util.HashSet;
import java.util.Set;

public class UserInfor {
    public Long id;
    public String name;
    public String username;
    public Long salary;
    public String email;
    public String address;
    public String mobileNo;
    public Set<String> roles = new HashSet<>();

    public UserInfor(Long id, String name, String username, Long salary, String email, String address, String mobileNo){
        this.id = id;
        this.name = name;
        this.username = username;
        this.salary = salary;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public void addRole(String role){
        this.roles.add(role);
    }
}