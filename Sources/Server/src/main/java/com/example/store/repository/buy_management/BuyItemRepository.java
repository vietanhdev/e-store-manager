package com.example.store.repository.buy_management;

import java.util.List;

import com.example.store.model.buy_management.BuyItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyItemRepository extends JpaRepository<BuyItem, Long> {
    List<BuyItem> findByBuyId(Long buy_id);

    @Query("SELECT SUM(e.quantities*e.price) FROM BuyItem e WHERE e.createdAt >= STR_TO_DATE(:start, '%Y-%m-%d %H:%i:%s') AND e.createdAt <= STR_TO_DATE(:end, '%Y-%m-%d %H:%i:%s')")
    Double totalBuy(String start, String end);
}