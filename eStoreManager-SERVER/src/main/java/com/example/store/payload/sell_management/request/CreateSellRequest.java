package com.example.store.payload.sell_management.request;

import java.util.HashSet;
import java.util.Set;

public class CreateSellRequest {

    private Long customer_id;

    private Float tax;

    private  Float total;

    private Set<SellItemInfor> sell_items = new HashSet<>();

    public Float getTax() {
        return tax;
    }

    public Float getTotal() {
        return total;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public Set<SellItemInfor> getSell_items() {
        return sell_items;
    }

}