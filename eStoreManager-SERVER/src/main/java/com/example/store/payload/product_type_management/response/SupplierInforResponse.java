package com.example.store.payload.product_type_management.response;

public class SupplierInforResponse {
    
    public Boolean success = true;

    public Long id;

    public String name;

    public Float price;

    public String unit;
    
    public String barcode;

    public SupplierInforResponse(String name, Float price, Integer quantities, String unit, String barcode){
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.barcode =barcode;
    }
    
}