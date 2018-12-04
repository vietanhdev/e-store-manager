package com.example.store.repository.sell_management;

import java.util.List;

import com.example.store.model.sell_management.SellItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellItemRepository extends JpaRepository<SellItem, Long> {
    List<SellItem> findBySellId(Long sell_id);
}