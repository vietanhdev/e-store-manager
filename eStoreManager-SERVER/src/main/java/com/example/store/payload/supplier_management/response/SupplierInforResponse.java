package com.example.store.payload.supplier_management.response;

public class SupplierInforResponse {
    public Boolean success = true;
    public Long id;
    public String name;
    public String email;
    public String address;
    public String mobileNo;

    public SupplierInforResponse(Long id, String name, String email, String address, String mobileNo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
    }
}