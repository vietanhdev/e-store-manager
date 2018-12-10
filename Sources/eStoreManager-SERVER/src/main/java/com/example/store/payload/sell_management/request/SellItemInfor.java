package com.example.store.payload.sell_management.request;

public class SellItemInfor {

    private Long product_id;

    private String product_name;

    private Float price;

    private Float quantities;

    private String unit;

    public SellItemInfor(Long product_id, String product_name, Float price, Float quantities, String unit) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.quantities = quantities;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public String getProduct_name() {
        return product_name;
    }

    public Long getProduct_id() {
        return this.product_id;
    }

    public Float getPrice() {
        return this.price;
    }

    public Float getQuantities() {
        return this.quantities;
    }

}