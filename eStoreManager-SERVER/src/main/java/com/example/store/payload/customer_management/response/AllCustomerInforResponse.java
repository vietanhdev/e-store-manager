package com.example.store.payload.customer_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllCustomerInforResponse {
    public Boolean success = true;
    public Set<CustomerInfor> customers = new HashSet<>();

    public AllCustomerInforResponse(){
    }
    
    public void addCustomerInfor(CustomerInfor customerInfor){
        this.customers.add(customerInfor);
    }

}