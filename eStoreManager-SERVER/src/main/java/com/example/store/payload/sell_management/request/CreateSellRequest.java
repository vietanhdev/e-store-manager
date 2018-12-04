package com.example.store.payload.sell_management.request;

import java.util.HashSet;
import java.util.Set;

public class CreateSellRequest {
    
    private Long user_id;

    private Long customer_id;

    private Float tax;

    private Set<SellItemInfor> sell_items = new HashSet<>();

    public Long getUser_id() {
        return user_id;
    }

    public Float getTax() {
        return tax;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public Set<SellItemInfor> getSell_items() {
        return sell_items;
    }

}