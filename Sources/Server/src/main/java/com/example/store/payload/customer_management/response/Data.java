package com.example.store.payload.customer_management.response;

public class Data {

    public Long id;
    
    public String name;

    public String email;

    public String address;

    public String mobileNo;

    public Data(Long id, String name, String email, String address, String mobileNo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
    }

}