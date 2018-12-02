package com.example.store.payload.supplier_management.response;

import java.util.HashSet;
import java.util.Set;

public class SearchSuppliersResponse {

    public boolean success = true;
    
    public Long draw;

    public Long recordsTotal;

    public Long recordsFiltered;

    public Set<Data> data = new HashSet<>();

    public SearchSuppliersResponse(Long draw, Long recordsTotal, Long recordsFiltered){
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public void addData( Data supplier ){
        data.add(supplier);
    }
}