package com.example.store.model.sell_management;

import javax.persistence.*;

import com.example.store.model.audit.DateAudit;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "sell_items")
public class SellItem extends DateAudit{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long product_id;

    private Float price;

    private Float quantities;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sell_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Sell sell;

    public SellItem() {
        
    }

    public SellItem(Long product_id, Float price, Float quantities) {
        this.product_id = product_id;
        this.price = price;
        this.quantities = quantities;
    }

    public Long getId() {
        return id;
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

    public Sell getSell(){
        return this.sell;
    }

    public void setSell(Sell sell){
        this.sell = sell;
    }
}
