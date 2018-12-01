package com.example.store.payload.customer_management.request;

public class SearchCustomersRequest {
    
    private Long draw;

    private Long start;

    private Long length;

    private Search search;

    public Long getDraw(){
        return draw;
    }

    public Long getStart(){
        return start;
    }

    public Long getLength(){
        return length;
    }

    public Search getSearch(){
        return search;
    }

}