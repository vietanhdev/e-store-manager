package com.example.store.payload.buy_management.response;

import java.util.ArrayList;
import java.util.List;

import com.example.store.payload.buy_management.request.BuyItemInfor;

public class Data {
    
    private Long id;

    private Long user_id;

    private String user_name;

    private Boolean active;

    private String createdAt;

    private List<BuyItemInfor> buy_items = new ArrayList<>();

    public Data(Long id, Long user_id, String user_name, Boolean active, String createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.active = active;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BuyItemInfor> getBuy_items() {
        return buy_items;
    }

    public void setBuy_items(List<BuyItemInfor> buy_items) {
        this.buy_items = buy_items;
    }

    public void addBuy_items(Long product_id, String product_name, Long supplier_id, String supplier_name, Float price, Float quantities, String unit){
        this.buy_items.add(new BuyItemInfor(product_id, product_name, supplier_id, supplier_name, price, quantities, unit));
    }

}