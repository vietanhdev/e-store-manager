package com.example.store.payload.customer_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllCustomerInforResponse {

    private Boolean success = true;

    private Set<CustomerInfor> customers = new HashSet<>();

    public AllCustomerInforResponse(){
    }
    
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void addCustomerInfor(CustomerInfor customerInfor) {
        this.customers.add(customerInfor);
    }

}