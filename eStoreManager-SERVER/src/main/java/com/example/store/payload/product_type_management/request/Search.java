package com.example.store.payload.product_type_management.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Search {

    @NotNull
    private String value;
    
    @NotNull
    private String name;

    @NotNull
    private Float price;

    @NotBlank
    private String unit;
    
    @NotNull
    private String barcode;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice(){
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

}