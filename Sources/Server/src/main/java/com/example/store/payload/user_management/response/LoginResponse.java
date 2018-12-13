package com.example.store.payload.user_management.response;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {
    private Boolean success = true;
    private Long id;
    private String name;
    private String username;
    private Long salary;
    private String email;
    private String address;
    private String mobileNo;
    private List<String> roles = new ArrayList<>();
    private String tokenType = "Bearer";
    private String accessToken;

    public LoginResponse(Long id, String name, String username, Long salary, String email, String address, String mobileNo, String accessToken){
        this.id = id;
        this.name = name;
        this.username = username;
        this.salary = salary;
        this.email = email;
        this.address = address;
        this. mobileNo = mobileNo;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    public List<String> getRole() {
        return this.roles;
    }

}