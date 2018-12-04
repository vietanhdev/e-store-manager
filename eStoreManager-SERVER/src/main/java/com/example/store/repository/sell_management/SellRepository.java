package com.example.store.repository.sell_management;

import com.example.store.model.sell_management.Sell;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {
    
}