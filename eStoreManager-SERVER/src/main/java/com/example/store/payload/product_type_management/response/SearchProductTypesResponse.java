package com.example.store.payload.product_type_management.response;

import java.util.HashSet;
import java.util.Set;

public class SearchProductTypesResponse {

    public boolean success = true;
    
    public Long draw;

    public Long recordsTotal;

    public Long recordsFiltered;

    public Set<Data> data = new HashSet<>();

    public SearchProductTypesResponse(Long draw, Long recordsTotal, Long recordsFiltered){
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public void addData( Data supplier ){
        data.add(supplier);
    }
}