package com.example.store.payload.product_type_management.response;

public class ProductTypeInforResponse {
    
    private Boolean success = true;

    private Long id;

    private String name;

    private Float price;

    private String unit;
    
    private String barcode;

    public ProductTypeInforResponse(Long id, String name, Float price, String unit, String barcode){
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
    
}