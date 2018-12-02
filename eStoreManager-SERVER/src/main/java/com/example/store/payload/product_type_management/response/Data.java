package com.example.store.payload.product_type_management.response;

public class Data {

    public Long id;

    public String name;

    public Float price;

    public String unit;
    
    public String barcode;

    public Data(String name, Float price, Integer quantities, String unit, String barcode){
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.barcode =barcode;
    }
    
}