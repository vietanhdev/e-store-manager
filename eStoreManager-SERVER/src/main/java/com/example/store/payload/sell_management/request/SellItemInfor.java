package com.example.store.payload.sell_management.request;

public class SellItemInfor {

    private Long product_id;

    private Float price;

    private Float quantities;

    public SellItemInfor(Long product_id, Float price, Float quantities) {
        this.product_id = product_id;
        this.price = price;
        this.quantities = quantities;
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