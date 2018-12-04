package com.example.store.payload.sell_management.request;

public class Search {
    
    private String value;

    private Long user_id;

    private Long customer_id;

    private String start;

    private String end;

    public String getValue(){
        return value;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setValue(String value) {
        this.value = value;
    }

}