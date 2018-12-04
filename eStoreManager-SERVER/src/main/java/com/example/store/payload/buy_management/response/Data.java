package com.example.store.payload.buy_management.response;

public class Data {
    
    private Long id;

    private Long user_id;

    public Data(Long id, Long user_id) {
        this.id = id;
        this.user_id = user_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}