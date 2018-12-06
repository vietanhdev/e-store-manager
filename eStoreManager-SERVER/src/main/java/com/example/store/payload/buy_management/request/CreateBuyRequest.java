package com.example.store.payload.buy_management.request;

import java.util.HashSet;
import java.util.Set;

public class CreateBuyRequest {

    private Set<BuyItemInfor> buy_items = new HashSet<>();

    public Set<BuyItemInfor> getBuy_items() {
        return buy_items;
    }

}