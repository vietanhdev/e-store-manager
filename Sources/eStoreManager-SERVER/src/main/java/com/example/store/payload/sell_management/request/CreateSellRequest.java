package com.example.store.payload.sell_management.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateSellRequest {

    private Long customer_id;

    @NotNull
    @Min(0)
    private Float tax;

    @NotNull
    @Min(0)
    private Float total;

    private List<SellItemInfor> sell_items = new ArrayList<>();

    public Float getTax() {
        return tax;
    }

    public Float getTotal() {
        return total;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public List<SellItemInfor> getSell_items() {
        return sell_items;
    }

}