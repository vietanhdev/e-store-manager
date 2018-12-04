package com.example.store.payload.product_management.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateProductRequest {

    @NotBlank
    @Size(min=4, max=40)
    private String name;

    @NotNull
    @Min(0)
    private Float price;

    @NotBlank
    @Size(max = 40)
    private String unit;
    
    @NotBlank
    @Size(max = 40) 
    private String barcode;

    @Min(0)
    private Float quantities;

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
    
    public Float getQuantities() {
        return quantities;
    }

}