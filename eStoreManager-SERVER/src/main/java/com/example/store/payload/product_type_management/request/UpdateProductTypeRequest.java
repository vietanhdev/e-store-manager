package com.example.store.payload.product_type_management.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class UpdateProductTypeRequest {
    
    @Size(min=4, max=40)
    private String name;

    @Min(0)
    private Float price;

    @Size(max = 40)
    private String unit;
    
    @Size(max = 40)
    private String barcode;

    public String getName() {
        return name;
    }

    public Float getPrice(){
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public String getBarcode() {
        return barcode;
    }

}