package com.example.store.payload.customer_management.response;

public class Data {

    private Long id;
    
    private String name;

    private String email;

    private String address;

    private String mobileNo;

    public Data(Long id, String name, String email, String address, String mobileNo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getAddress(){
        return address;
    }

    public String getMobileNo(){
        return mobileNo;
    }

}