package com.example.store.payload.supplier_management.response;

import java.util.HashSet;
import java.util.Set;

public class AllSupplierInforResponse {
    public Boolean success = true;
    public Set<SupplierInfor> suppliers = new HashSet<>();

    public AllSupplierInforResponse(){
    }
    
    public void addsupplierInfor(SupplierInfor supplierInfor){
        this.suppliers.add(supplierInfor);
    }

}