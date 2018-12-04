package com.example.store.payload.buy_management.request;

public class BuyItemInfor {

    private Long product_id;

    private Long supplier_id;

    private Float price;

    private Long quantities;

    public BuyItemInfor(Long product_id, Long supplier_id, Float price, Long quantities) {
        this.product_id = product_id;
        this.supplier_id = supplier_id;
        this.price = price;
        this.quantities = quantities;
    }

    public Long getProduct_id() {
        return this.product_id;
    }

    public Long getSupplier_id() {
        return this.supplier_id;
    }

    public Float getPrice() {
        return this.price;
    }

    public Long getQuantities() {
        return this.quantities;
    }

}