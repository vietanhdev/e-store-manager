package com.example.store.model.product_management;

import javax.persistence.*;

import com.example.store.model.audit.DateAudit;

@Entity
@Table(name = "order_bills")
public class OrderBill extends DateAudit{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String user_id;

    private String customer_id;

    public OrderBill() {

    }

    public OrderBill(String user_id, String customer_id) {
        this.user_id = user_id;
        this.customer_id = customer_id;
    }

    public String getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

}