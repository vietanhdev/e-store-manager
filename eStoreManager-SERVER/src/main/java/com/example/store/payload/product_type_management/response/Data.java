package com.example.store.payload.product_type_management.response;

public class Data {

    public Long id;

    public String name;

    public Float price;

    public String unit;
    
    public String barcode;

    public Long quantities;

    public Data(Long id, String name, Float price , String unit, String barcode, Long quantities){
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.barcode = barcode;
        this.quantities = quantities;
    }
}