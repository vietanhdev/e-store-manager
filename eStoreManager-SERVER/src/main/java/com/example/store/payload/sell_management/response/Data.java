package com.example.store.payload.sell_management.response;

public class Data {
    
    private Long id;

    private Long user_id;

    private Long customer_id;

    private Float tax;

    public Data(Long id, Long user_id, Long customer_id, Float tax) {
        this.id = id;
        this.user_id = user_id;
        this.customer_id = customer_id;
        this.tax = tax;
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

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

}