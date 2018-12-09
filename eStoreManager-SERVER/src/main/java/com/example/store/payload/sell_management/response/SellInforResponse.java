package com.example.store.payload.sell_management.response;

import java.util.ArrayList;
import java.util.List;

import com.example.store.payload.sell_management.request.SellItemInfor;

public class SellInforResponse {

    private Boolean success = true;

    private Long id;

    private Long user_id;

    private String user_name;

    private Long customer_id;

    private String customer_name;

    private Float tax;

    private Float total;

    private Boolean active;

    private String createdAt;

    private List<SellItemInfor> buy_items = new ArrayList<>();

    public SellInforResponse(Long id, Long user_id, String user_name, Long customer_id, String customer_name, Float tax, Float total, Boolean active, String createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.tax = tax;
        this.total = total;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    public List<SellItemInfor> getSell_items() {
        return buy_items;
    }

    public void setSell_items(List<SellItemInfor> buy_items) {
        this.buy_items = buy_items;
    }

    public void addSell_items(Long product_id, String product_name, Float price, Float quantities, String unit){
        this.buy_items.add(new SellItemInfor(product_id, product_name, price, quantities, unit));
    }
}
