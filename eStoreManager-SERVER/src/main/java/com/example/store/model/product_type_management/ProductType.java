package com.example.store.model.product_type_management;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.example.store.model.audit.DateAudit;

@Entity
@Table(name = "product_types")
public class ProductType extends DateAudit{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotNull
    @Min(0)
    private Float price;

    @NotBlank
    @Size(max = 40)
    private String unit;
    
    @NotBlank
    @Size(max = 40)
    private String barcode;

    public ProductType(){

    }

    public ProductType(String name, Float price, Integer quantities, String unit, String barcode){
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.barcode =barcode;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice(){
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBarcode() {
        return barcode;
    }

    public void barcode(String barcode) {
        this.barcode = barcode;
    }

}