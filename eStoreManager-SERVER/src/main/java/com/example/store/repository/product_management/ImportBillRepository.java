package com.example.store.repository.product_management;

import com.example.store.model.product_management.ImportBill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportBillRepository extends JpaRepository<ImportBill, Long> {
    
}