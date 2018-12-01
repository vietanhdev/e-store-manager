package com.example.store.payload.customer_management.request;

public class Search {

    private String data;
    
    private String name;

    private String email;

    private String address;

    private String mobileNo;

    public String getData(){
        return data;
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