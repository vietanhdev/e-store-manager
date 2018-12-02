package com.example.store.model.product_management;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.example.store.model.audit.DateAudit;
import com.example.store.model.product_type_management.ProductType;
import com.example.store.model.supplier_management.Supplier;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "products")
public class Product extends DateAudit{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductType product_type;
    

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Supplier supplier;
    
    @NotNull
    @Min(0)
    private Integer quantities;

    public Product(){

    }

    public Product(ProductType product_type, Supplier supplier, Integer quantities){
        this.product_type = product_type;
        this.supplier = supplier;
        this.quantities = quantities;
    } 

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public ProductType getProductType(){
        return product_type;
    }

    public void setProductType(ProductType product_type){
        this.product_type = product_type;
    }

    public Supplier getSupplier(){
        return supplier;
    }

    public void setSupplier(Supplier supplier){
        this.supplier = supplier;
    }

    public Integer getQuantities() {
        return quantities;
    }

    public void setQuantities(Integer quantities) {
        this.quantities = quantities;
    }

}