package com.example.store.payload.customer_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllCustomerSummaryResponse {
    public Boolean success;
    public Set<CustomerSummary> customer = new HashSet<>();

    public AllCustomerSummaryResponse(Boolean success){
        this.success = success;
    }

    public void addCustomer(Long id, String name){
        this.customer.add(new CustomerSummary(id, name));
    }

}