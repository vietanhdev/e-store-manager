package com.example.store.payload.buy_management.request;

import java.util.HashSet;
import java.util.Set;

public class UpdateBuyRequest {
    
    private Long user_id;

    private Boolean active;

    private Set<BuyItemInfor> buy_items = new HashSet<>();

    public Long getUser_id() {
        return user_id;
    }

    public Boolean getActive() {
        return active;
    }

    public Set<BuyItemInfor> getBuy_items() {
        return buy_items;
    }

}