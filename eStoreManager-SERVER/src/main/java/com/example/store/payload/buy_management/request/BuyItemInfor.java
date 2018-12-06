package com.example.store.payload.buy_management.request;

public class BuyItemInfor {

    private Long product_id;

    private String product_name;

    private Long supplier_id;

    private String supplier_name;

    private Float price;

    private Float quantities;

    private String unit;

    public BuyItemInfor(Long product_id, String product_name, Long supplier_id, String supplier_name, Float price, Float quantities, String unit) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.price = price;
        this.quantities = quantities;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getProduct_name() {
        return product_name;
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

    public Float getQuantities() {
        return this.quantities;
    }

}