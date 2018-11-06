package com.example.store.payload;

import javax.validation.constraints.*;

public class ChangeUserInforRequest {

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String username;
    
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @Size(max = 100)
    private String address;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String mobileNo;

    public ChangeUserInforRequest(String name, String username, String email, String address, String mobileNo) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
    }

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

}