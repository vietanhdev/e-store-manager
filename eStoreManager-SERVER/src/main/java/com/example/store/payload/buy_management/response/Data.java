package com.example.store.payload.buy_management.response;

import java.util.HashSet;
import java.util.Set;

import com.example.store.payload.buy_management.request.BuyItemInfor;

public class Data {
    
    private Long id;

    private Long user_id;

    private Boolean active;

    private Set<BuyItemInfor> buy_items = new HashSet<>();

    public Data(Long id, Long user_id, Boolean active) {
        this.id = id;
        this.user_id = user_id;
        this.active = active;
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

    public Set<BuyItemInfor> getBuy_items() {
        return buy_items;
    }

    public void setBuy_items(Set<BuyItemInfor> buy_items) {
        this.buy_items = buy_items;
    }

    public void addBuy_items(Long product_id, Long supplier_id, Float price, Float quantities){
        this.buy_items.add(new BuyItemInfor(product_id, supplier_id, price, quantities));
    }

}