package com.example.store.model.product_management;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.example.store.model.audit.DateAudit;

@Entity
@Table(name = "products")
public class Product extends DateAudit{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private ProductType product_type;

    @NotNull
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