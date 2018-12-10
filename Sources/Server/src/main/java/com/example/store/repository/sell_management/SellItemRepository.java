package com.example.store.repository.sell_management;

import java.util.List;

import com.example.store.model.sell_management.SellItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellItemRepository extends JpaRepository<SellItem, Long> {
    List<SellItem> findBySellId(Long sell_id);

    @Query("SELECT new  com.example.store.repository.sell_management.BestSelling(SUM(e.quantities), e.product_id) FROM SellItem e WHERE e.createdAt >= STR_TO_DATE(:start, '%Y-%m-%d %H:%i:%s') AND e.createdAt <= STR_TO_DATE(:end, '%Y-%m-%d %H:%i:%s')  GROUP BY product_id ORDER BY SUM(e.quantities) ASC")
    List<BestSelling> bestSellings(String start, String end);

    @Query("SELECT SUM(e.quantities*e.price) FROM SellItem e WHERE e.createdAt >= STR_TO_DATE(:start, '%Y-%m-%d %H:%i:%s') AND e.createdAt <= STR_TO_DATE(:end, '%Y-%m-%d %H:%i:%s')")
    Double totalSell(String start, String end);
}