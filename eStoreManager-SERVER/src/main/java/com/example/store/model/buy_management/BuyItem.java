package com.example.store.model.buy_management;

import javax.persistence.*;

import com.example.store.model.audit.DateAudit;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "buy_items")
public class BuyItem extends DateAudit{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long product_id;

    private Long supplier_id;

    private Float price;

    private Float quantities;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buy_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Buy buy;

    public BuyItem(){
        
    }

    public BuyItem(Long product_id, Long supplier_id, Float price, Float quantities) {
        this.product_id = product_id;
        this.supplier_id = supplier_id;
        this.price = price;
        this.quantities = quantities;
    }

    public Long getId() {
        return id;
    }

    public Long getSupplierId() {
        return supplier_id;
    }

    public void setSupplierId(Long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public Float getQuantities() {
        return quantities;
    }

    public void setQuantities(Float quantities) {
        this.quantities = quantities;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getProductId() {
        return product_id;
    }

    public void setProductId(Long product_id) {
        this.product_id = product_id;
    }

    public Buy getBuy() {
        return this.buy;
    }

    public void setBuy(Buy buy) {
        this.buy = buy;
    }
}
