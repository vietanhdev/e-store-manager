package com.example.store.payload.buy_management.request;

public class Search {
    
    private String value;

    private Long user_id;

    private String start;

    private String end;

    public String getValue(){
        return value;
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