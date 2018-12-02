package com.example.store.payload.supplier_management.response;

import java.util.HashSet;
import java.util.Set;

public class SearchSuppliersResponse {

    public boolean success = true;
    
    public int draw;

    public int recordsTotal;

    public int recordsFiltered;

    public Set<Data> data = new HashSet<>();

    public SearchSuppliersResponse(int draw, int recordsTotal, int recordsFiltered){
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public void addData( Data supplier ){
        data.add(supplier);
    }
}