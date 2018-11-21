package com.example.store.repository.bill_management;

import com.example.store.model.bill_management.ImportBill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportBillRepository extends JpaRepository<ImportBill, Long> {
    
}