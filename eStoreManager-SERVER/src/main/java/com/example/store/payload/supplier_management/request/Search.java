package com.example.store.payload.supplier_management.request;

import javax.validation.constraints.NotNull;

public class Search {
    
    @NotNull
    private String value;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String address;

    @NotNull
    private String mobileNo;

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getMobileNo(){
        return mobileNo;
    }

    public void setMobileNo(String mobileNo){
        this.mobileNo = mobileNo;
    }

}