package com.example.store.payload.report_management.response;

import java.util.ArrayList;
import java.util.List;

public class Report1Response {

    private boolean success = true;

    private Float cost;

    private Float revenue;

    private List<ProductInfor> products = new ArrayList<>();

    public Report1Response() {

    }

    public Report1Response(Float cost, Float revenue) {
        this.cost = cost;
        this.revenue = revenue;
    }

    public List<ProductInfor> getProducts() {
        return products;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getRevenue() {
        return revenue;
    }

    public void setRevenue(Float revenue) {
        this.revenue = revenue;
    }

    public void setProducts(List<ProductInfor> products) {
        this.products = products;
    }

    public void addProduct(Long id, String name, Float price, String unit, String barcode, Float quantities, Double sold_quantities){
        this.products.add(new ProductInfor(id, name, price, unit, barcode, quantities, sold_quantities));
    }
    
}