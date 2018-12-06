package com.example.store.payload.sell_management.response;

import java.util.HashSet;
import java.util.Set;

import com.example.store.payload.sell_management.request.SellItemInfor;

public class SellInforResponse {

    private Boolean success = true;

    private Long id;

    private Long user_id;

    private Long customer_id;

    private Float tax;

    private Boolean active;

    private Set<SellItemInfor> buy_items = new HashSet<>();

    public SellInforResponse(Long id, Long user_id, Long customer_id, Float tax, Boolean active) {
        this.id = id;
        this.user_id = user_id;
        this.customer_id = customer_id;
        this.tax = tax;
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    
    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Set<SellItemInfor> getSell_items() {
        return buy_items;
    }

    public void setSell_items(Set<SellItemInfor> buy_items) {
        this.buy_items = buy_items;
    }

    public void addSell_items(Long product_id, Float price, Float quantities){
        this.buy_items.add(new SellItemInfor(product_id, price, quantities));
    }
}
