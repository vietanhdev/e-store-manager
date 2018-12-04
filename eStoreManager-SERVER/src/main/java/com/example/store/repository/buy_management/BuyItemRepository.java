package com.example.store.repository.buy_management;

import java.util.List;

import com.example.store.model.buy_management.BuyItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyItemRepository extends JpaRepository<BuyItem, Long> {
    List<BuyItem> findByBuyId(Long buy_id);
}