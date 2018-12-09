package com.example.store.payload.customer_management.response;

import java.util.ArrayList;
import java.util.List;

public class AllCustomerInforResponse {

    private Boolean success = true;

    private List<CustomerInfor> customers = new ArrayList<>();

    public AllCustomerInforResponse(){
    }
    
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<CustomerInfor> getCustomers(){
        return customers;
    }

    public void setCustomers(List<CustomerInfor> customers){
        this.customers = customers;
    }

    public void addCustomerInfor(CustomerInfor customerInfor) {
        this.customers.add(customerInfor);
    }

}