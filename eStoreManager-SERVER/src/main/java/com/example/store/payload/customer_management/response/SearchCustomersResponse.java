package com.example.store.payload.customer_management.response;

import java.util.HashSet;
import java.util.Set;

public class SearchCustomersResponse {
    
    public int draw;

    public int recordsTotal;

    public int recordsFiltered;

    public Set<DataCustomer> data = new HashSet<>();

    public SearchCustomersResponse(int draw, int recordsTotal, int recordsFiltered){
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public void addData( DataCustomer customer ){
        data.add(customer);
    }
}