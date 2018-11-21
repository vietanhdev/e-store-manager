package com.example.store.repository.bill_management;

import com.example.store.model.bill_management.OrderBill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBillRepository extends JpaRepository<OrderBill, Long> {
    
}