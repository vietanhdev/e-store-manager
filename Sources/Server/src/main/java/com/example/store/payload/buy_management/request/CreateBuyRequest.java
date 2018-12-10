package com.example.store.payload.buy_management.request;

import java.util.ArrayList;
import java.util.List;

public class CreateBuyRequest {

    private List<BuyItemInfor> buy_items = new ArrayList<>();

    public List<BuyItemInfor> getBuy_items() {
        return buy_items;
    }

}