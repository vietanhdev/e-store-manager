package com.example.store.payload.product_management.response;

import java.util.HashSet;
import java.util.Set;

public class SearchProductsResponse {

    private boolean success = true;
    
    private Long draw;

    private Long recordsTotal;

    private Long recordsFiltered;

    private Set<Data> data = new HashSet<>();

    public SearchProductsResponse(Long draw, Long recordsTotal, Long recordsFiltered){
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getDraw() {
        return draw;
    }

    public void setDraw(Long draw) {
        this.draw = draw;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void addData(Data product_type) {
        data.add(product_type);
    }

    public Set<Data> getData(){
        return this.data;
    }

    public void setData(Set<Data> data){
        this.data = data;
    }
}