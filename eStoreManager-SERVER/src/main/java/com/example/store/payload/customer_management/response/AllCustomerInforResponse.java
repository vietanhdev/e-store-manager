package com.example.store.payload.customer_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllCustomerInforResponse {
    public Boolean success;
    public Set<CustomerInfor> customers = new HashSet<>();

    public AllCustomerInforResponse(Boolean success){
        this.success = success;
    }
    
    public void addCustomerInfor(CustomerInfor customerInfor){
        this.customers.add(customerInfor);
    }

}