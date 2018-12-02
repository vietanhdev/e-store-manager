package com.example.store.payload.customer_management.response;

public class Data {

    private Long id;
    
    private String name;

    private String email;

    private String address;

    private String mobileNo;

    public Data(Long id, String name, String email, String address, String mobileNo) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
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

}