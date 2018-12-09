package com.example.store.payload.buy_management.request;

import java.util.ArrayList;
import java.util.List;

public class UpdateBuyRequest {
    
    private Long user_id;

    private Boolean active;

    private List<BuyItemInfor> buy_items = new ArrayList<>();

    public Long getUser_id() {
        return user_id;
    }

    public Boolean getActive() {
        return active;
    }

    public List<BuyItemInfor> getBuy_items() {
        return buy_items;
    }

}