package com.example.store.payload.sell_management.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateSellRequest {
    
    private Long user_id;

    private Long customer_id;

    @NotNull
    @Min(0)
    private Float tax;

    @NotNull
    @Min(0)
    private Float total;

    private Boolean active;

    private List<SellItemInfor> sell_items = new ArrayList<>();

    public Long getUser_id() {
        return user_id;
    }

    public Float getTotal() {
        return total;
    }

    public Float getTax() {
        return tax;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public Boolean getActive() {
        return active;
    }

    public List<SellItemInfor> getSell_items() {
        return sell_items;
    }
}