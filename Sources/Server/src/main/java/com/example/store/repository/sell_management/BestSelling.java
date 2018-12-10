package com.example.store.repository.sell_management;

public class BestSelling {
    private Double sold_quantities;

    private Long product_id;

    public BestSelling(Double sold_quantities, Long product_id) {
        this.sold_quantities = sold_quantities;
        this.product_id = product_id;
    }

    public Double getSold_quantities() {
        return sold_quantities;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public void setSold_quantities(Double sold_quantities) {
        this.sold_quantities = sold_quantities;
    }
}