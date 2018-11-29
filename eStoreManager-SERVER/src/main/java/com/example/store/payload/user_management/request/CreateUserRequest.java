package com.example.store.payload.user_management.request;

import java.util.List;

import javax.validation.constraints.*;

public class CreateUserRequest {

    @Size(min=4, max=40)
    private String name;
    
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @Size(max = 100)
    private String address;

    private String mobileNo;

    @Min(0)
    private Long salary;

    private List<String> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getSalary(){
        return salary;
    }

    public void setSalary(Long salary){
        this.salary = salary;
    }
    
}