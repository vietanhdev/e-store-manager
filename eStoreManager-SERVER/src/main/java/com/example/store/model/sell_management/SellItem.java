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

    private Long quantities;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sell_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Sell sell;

    public SellItem() {
        
    }

    public SellItem(Long product_id, Float price, Long quantities) {
        this.setProduct_id(product_id);
        this.setPrice(price);
        this.setQuantities(quantities);
    }

    public Long getQuantities() {
        return quantities;
    }

    public void setQuantities(Long quantities) {
        this.quantities = quantities;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Sell getSell(){
        return this.sell;
    }

    public void setSell(Sell sell){
        this.sell = sell;
    }
}
