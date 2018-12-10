package com.example.store.payload.report_management.response;

public class ProductInfor {
    
    private Boolean success = true;

    private Long id;

    private String name;

    private Float price;

    private String unit;
    
    private String barcode;

    private Float quantities;

    private Double sold_quantities;

    public ProductInfor(Long id, String name, Float price, String unit, String barcode, Float quantities, Double sold_quantities){
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.barcode = barcode;
        this.quantities = quantities;
        this.sold_quantities = sold_quantities;
    }

    public Double getSold_quantities() {
        return sold_quantities;
    }

    public void setSold_quantities(Double sold_quantities) {
        this.sold_quantities = sold_quantities;
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

    public Float getQuantities() {
        return quantities;
    }

    public void setQuantities(Float quantities) {
        this.quantities = quantities;
    }
    
}